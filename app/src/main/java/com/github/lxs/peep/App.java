package com.github.lxs.peep;

import android.app.Application;

import com.github.lxs.peep.http.HttpUrl;
import com.github.lxs.peep.http.HttpUtils;
import com.github.lxs.peep.http.NetWorkConfiguration;
import com.socks.library.KLog;

public class App extends Application {

//    public static RefWatcher sRefWatcher;


    private static App application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        // 初始化log
        KLog.init(Constants.SHOW_DEBUG, "Peep");

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        sRefWatcher = LeakCanary.install(this);

        initOkHttp();
    }

    private void initOkHttp() {
        NetWorkConfiguration configuration = new NetWorkConfiguration(this)
                .baseUrl(HttpUrl.baseUrl)
                .isCache(true)
                .isDiskCache(true)
                .isMemoryCache(true);
        HttpUtils.setConFiguration(configuration);
    }

    public static App getApplication() {
        return application;
    }
}
