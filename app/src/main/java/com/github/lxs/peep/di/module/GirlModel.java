package com.github.lxs.peep.di.module;

import com.github.lxs.peep.di.scope.FragmentScope;
import com.github.lxs.peep.ui.girl.view.IGirlView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cl on 2017/4/6.
 */

@Module
public class GirlModel {

    private IGirlView mIGirlView;

    public GirlModel(IGirlView IGirlView) {
        mIGirlView = IGirlView;
    }

    @FragmentScope
    @Provides
    public IGirlView providesIGirlView() {
        return mIGirlView;
    }
}
