package com.github.lxs.peep.ui.girl.model;

import com.github.lxs.peep.bean.girl.GirlInfo;
import com.github.lxs.peep.bean.test;
import com.github.lxs.peep.http.ApiManager;
import com.github.lxs.peep.http.HttpUrl;
import com.github.lxs.peep.http.HttpUtils;
import com.github.lxs.peep.http.RetrofitClient;
import com.github.lxs.peep.http.response.BaseHttpResponse;
import com.github.lxs.peep.http.response.GirlHttpResponse;
import com.github.lxs.peep.listener.MySubscriber;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cl on 2017/4/6.
 */

public class GirlModel {

    public void loadGirlData(int index, int limit, String tag1, String tag2, OnLoadCompleteListener<List<GirlInfo>> listener) {
//        RetrofitClient r =  HttpUtils.getInstance()
//                .getRetofitClinet();
//                .setBaseUrl(HttpUrl.GIRL_BASE_URL)
//                .builder(ApiManager.GirlApi.class);
//        HttpUtils.getInstance()
//                .getRetofitClinet()
//                .setBaseUrl(HttpUrl.GIRL_BASE_URL)
//                .builder(ApiManager.GirlApi.class)
//                .getGirls(index, limit, tag1, tag2)
//                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MySubscriber<String>() {
//                    @Override
//                    public void onSuccess(String test) {
//                        KLog.e(test);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        KLog.e("失败");
//                    }
//                });
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://image.baidu.com/channel/listjson?ie=utf8&pn="+index+"&rn="+limit+"&tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8")
                .get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                KLog.e("失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string().toString();
                    JSONObject obj = new JSONObject(str);
                    Gson g = new Gson();
                    ArrayList<GirlInfo> info = g.fromJson(obj.getString("data"), new TypeToken<ArrayList<GirlInfo>>() {
                    }.getType());
                    info.remove(info.size() - 1);
                    listener.onLoadSuccess(info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(HttpUrl.GIRL_BASE_URL).addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        retrofit.create(ApiManager.GirlApi.class).getGirls(index, limit, tag1, tag2).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MySubscriber<String>() {
//                    @Override
//                    public void onSuccess(String s) {
//                        KLog.e(s);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        KLog.e("失败");
//                    }
//                });

//        HttpUtils.getInstance()
//                .getRetofitClinet()
//                .setBaseUrl(HttpUrl.GIRL_BASE_URL)
//                .builder(ApiManager.GirlApi.class)
//                .getGirls(index, limit, tag1, tag2)
//                .observeOn(Schedulers.io())
//                .map(new Func1<GirlHttpResponse<List<GirlInfo>>, List<GirlInfo>>() {
//                    @Override
//                    public List<GirlInfo> call(GirlHttpResponse<List<GirlInfo>> listGirlHttpResponse) {
//                        KLog.e("listGirlHttpResponse---" + listGirlHttpResponse.getTag1());
//                        return listGirlHttpResponse.getData();
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MySubscriber<List<GirlInfo>>() {
//                    @Override
//                    public void onSuccess(List<GirlInfo> girlInfos) {
//                        KLog.e("成功---" + girlInfos);
//                        listener.onLoadSuccess(girlInfos);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        KLog.e("失败");
//                        KLog.e(e.getMessage());
//                        listener.onLoadFailed("失败");
//                    }
//                });
    }
}
