package com.github.lxs.peep.di.component;

import javax.inject.Singleton;

import dagger.Component;
import com.github.lxs.peep.App;
import com.github.lxs.peep.di.module.AppModule;
import com.github.lxs.peep.di.scope.ContextLife;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();  // 提供App的Context

}
