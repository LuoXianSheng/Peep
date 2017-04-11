package com.github.lxs.peep;

import android.app.Application;
import android.content.Context;

import com.github.lxs.peep.http.HttpUrl;
import com.github.lxs.peep.http.HttpUtils;
import com.github.lxs.peep.http.NetWorkConfiguration;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class App extends Application {

    private RefWatcher mRefWatcher;


    private static App application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        // 初始化log
        KLog.init(Constants.SHOW_DEBUG, "Peep");

        mRefWatcher = LeakCanary.install(this);

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

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context
                .getApplicationContext();
        return application.mRefWatcher;
    }
}
