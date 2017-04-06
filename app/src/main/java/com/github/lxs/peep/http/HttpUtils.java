package com.github.lxs.peep.http;

import android.content.Context;

import com.github.lxs.peep.App;
import com.github.lxs.peep.utils.NetworkUtils;
import com.socks.library.KLog;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 类描述：   对OkHttpClient进行配置
 **/
public class HttpUtils {

    public static final String TAG = "HttpUtils";

    //    获得HttpUtils实例
    private static HttpUtils mInstance;
    //    OkHttpClient对象
    private OkHttpClient mOkHttpClient;
    private static NetWorkConfiguration configuration;
    private Context context;
    /**
     * 是否加载本地缓存数据
     * 默认为TRUE
     */
    private boolean isLoadDiskCache = true;

    /**
     * ---> 针对无网络情况
     * 是否加载本地缓存数据
     *
     * @param isCache true为加载 false不进行加载
     * @return
     */
    public HttpUtils setLoadDiskCache(boolean isCache) {
        this.isLoadDiskCache = isCache;
        return this;
    }

    /**
     * ---> 针对有网络情况
     * 是否加载内存缓存数据
     * 默认为False
     */
    private boolean isLoadMemoryCache = false;

    /**
     * 是否加载内存缓存数据
     *
     * @param isCache true为加载 false不进行加载
     * @return
     */
    public HttpUtils setLoadMemoryCache(boolean isCache) {
        this.isLoadMemoryCache = isCache;
        return this;
    }

    public HttpUtils(Context context) {
//创建默认 okHttpClient对象
        this.context = context;
        /**进行默认配置
         *    未配置configuration ,
         *
         */
        if (configuration == null) {
            configuration = new NetWorkConfiguration(context);
        }
        if (configuration.getIsCache()) {
            mOkHttpClient = new OkHttpClient.Builder()
//                   网络缓存拦截器
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(interceptor)
//                    自定义网络Log显示
                    .addInterceptor(mLoggingInterceptor)
                    .cache(configuration.getDiskCache())
                    .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(configuration.getConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();
        } else {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(mLoggingInterceptor)
                    .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(configuration.getConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();

        }
    }

    /**
     * 设置网络配置参数
     *
     * @param configuration
     */
    public static void setConFiguration(NetWorkConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("ImageLoader configuration can not be initialized with null");
        } else {
            if (HttpUtils.configuration == null) {
                KLog.d("Initialize NetWorkConfiguration with configuration");
                HttpUtils.configuration = configuration;
            } else {
                KLog.e("Try to initialize NetWorkConfiguration which had already been initialized before. To re-init NetWorkConfiguration with new configuration ");
            }
        }
        if (configuration != null) {
            KLog.i("ConFiguration" + configuration.toString());
        }
    }

    public RetrofitClient getRetofitClinet() {

        KLog.i("configuration:" + configuration.toString());
        return new RetrofitClient(configuration.getBaseUrl(), mOkHttpClient);
    }

    /**
     * 设置是否打印网络日志
     *
     * @param falg
     */
    public HttpUtils setDBugLog(boolean falg) {
        if (falg) {
            mOkHttpClient = getOkHttpClient().newBuilder()
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return this;
    }

    /**
     * 设置Coolie
     *
     * @return
     */
//    public HttpUtils addCookie() {
//        mOkHttpClient = getOkHttpClient().newBuilder()
//                .cookieJar(new SimpleCookieJar())
//                .build();
//        return this;
//    }

    /**
     * 获得OkHttpClient实例
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 网络拦截器
     * 进行网络操作的时候进行拦截
     */
    final Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            /**
             *  断网后是否加载本地缓存数据
             *
             */
            if (!NetworkUtils.isConnected(configuration.context) && isLoadDiskCache) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
//            加载内存缓存数据
            else if (isLoadMemoryCache) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            /**
             *  加载网络数据
             */
            else {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            }
            Response response = chain.proceed(request);
//            有网进行内存缓存数据
            if (NetworkUtils.isConnected(configuration.context) && configuration.getIsMemoryCache()) {
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + configuration.getmemoryCacheTime())
                        .removeHeader("Pragma")
                        .build();
            } else {
//              进行本地缓存数据
                if (configuration.getIsDiskCache()) {
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + configuration.getDiskCacheTime())
                            .removeHeader("Pragma")
                            .build();
                }
            }
            return response;
        }
    };

    /**
     * 获取请求网络实例
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils(App.getApplication());
                }
            }
        }
        return mInstance;
    }


    // 打印返回的json数据拦截器
    private static Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request request = chain.request();
            KLog.e("URL", request.url().toString());

            final Response response = chain.proceed(request);

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    KLog.e("");
                    KLog.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
//                KLog.v("--------------------------------------------开始打印返回数据----------------------------------------------------");
//                KLog.json(buffer.clone().readString(charset));
//                KLog.v("--------------------------------------------结束打印返回数据----------------------------------------------------");
            }

            return response;
        }
    };

}
