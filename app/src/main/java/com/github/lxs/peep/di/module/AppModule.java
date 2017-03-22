package com.github.lxs.peep.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.github.lxs.peep.App;
import com.github.lxs.peep.di.scope.ContextLife;

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    App provideApplicationContext() {
        return application;
    }

}
