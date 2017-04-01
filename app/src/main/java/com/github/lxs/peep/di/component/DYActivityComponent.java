package com.github.lxs.peep.di.component;

import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.di.scope.ActivityScope;
import com.github.lxs.peep.ui.dy.ui.LivePlayActivity;

import dagger.Component;

/**
 * Created by cl on 2017/4/1.
 */

@ActivityScope
@Component(modules = DYModule.class)
public interface DYActivityComponent {

    void inject(LivePlayActivity livePlayActivity);
}
