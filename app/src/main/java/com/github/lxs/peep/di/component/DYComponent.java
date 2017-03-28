package com.github.lxs.peep.di.component;

import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.di.scope.FragmentScope;
import com.github.lxs.peep.ui.dy.ui.IndexFragment;

import dagger.Component;

/**
 * Created by cl on 2017/3/28.
 */

@FragmentScope
@Component(modules = DYModule.class)
public interface DYComponent {

    void inject(IndexFragment indexFragment);
}
