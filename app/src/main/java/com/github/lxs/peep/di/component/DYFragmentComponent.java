package com.github.lxs.peep.di.component;

import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.di.scope.FragmentScope;
import com.github.lxs.peep.ui.dy.ui.AllLiveFragment;
import com.github.lxs.peep.ui.dy.ui.DYFragment;
import com.github.lxs.peep.ui.dy.ui.IndexFragment;
import com.github.lxs.peep.ui.dy.ui.IndexOtherMenuFragment;
import com.github.lxs.peep.ui.dy.ui.IndexRecommendFragment;

import dagger.Component;

/**
 * Created by cl on 2017/3/28.
 */

@FragmentScope
@Component(modules = DYModule.class)
public interface DYFragmentComponent {

    void inject(IndexRecommendFragment indexRecommendFragment);

    void inject(IndexFragment indexFragment);

    void inject(IndexOtherMenuFragment indexOtherMenuFragment);

    void inject(AllLiveFragment allLiveFragment);

    void inject(DYFragment dyFragment);

}
