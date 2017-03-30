package com.github.lxs.peep.di.module;

import com.github.lxs.peep.di.scope.FragmentScope;
import com.github.lxs.peep.ui.dy.view.IIndexView;
import com.github.lxs.peep.ui.dy.view.IOtherMenuView;
import com.github.lxs.peep.ui.dy.view.IRecomView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cl on 2017/3/28.
 */

@Module
public class DYModule {

    private IRecomView mIRecomView;
    private IIndexView mIIndexView;
    private IOtherMenuView iOtherMenuView;

    public DYModule(IRecomView mIRecomView) {
        this.mIRecomView = mIRecomView;
    }

    public DYModule(IIndexView mIIndexView) {
        this.mIIndexView = mIIndexView;
    }

    public DYModule(IOtherMenuView iOtherMenuView) {
        this.iOtherMenuView = iOtherMenuView;
    }

    @FragmentScope
    @Provides
    public IRecomView providesIRecomView() {
        return mIRecomView;
    }

    @FragmentScope
    @Provides
    public IIndexView providesIIndexView() {
        return mIIndexView;
    }

    @FragmentScope
    @Provides
    public IOtherMenuView providesIOtherMenuView() {
        return iOtherMenuView;
    }
}
