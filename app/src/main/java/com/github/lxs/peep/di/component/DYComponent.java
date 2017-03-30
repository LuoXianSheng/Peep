package com.github.lxs.peep.di.component;

import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.di.scope.FragmentScope;
import com.github.lxs.peep.ui.dy.ui.IndexFragment;
import com.github.lxs.peep.ui.dy.ui.OtherMenuFragment;
import com.github.lxs.peep.ui.dy.ui.RecommendFragment;

import dagger.Component;

/**
 * Created by cl on 2017/3/28.
 */

@FragmentScope
@Component(modules = DYModule.class)
public interface DYComponent {

    void inject(RecommendFragment recommendFragment);

    void inject(IndexFragment indexFragment);

    void inject(OtherMenuFragment otherMenuFragment);
}
