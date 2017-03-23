package com.github.lxs.peep;

import android.app.Application;

import com.github.lxs.peep.di.component.AppComponent;
import com.github.lxs.peep.di.component.DaggerAppComponent;
import com.github.lxs.peep.di.module.AppModule;
import com.socks.library.KLog;

public class App extends Application {

//    public static RefWatcher sRefWatcher;

    private AppComponent appComponent;

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

        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static App getApplication() {
        return application;
    }
}
