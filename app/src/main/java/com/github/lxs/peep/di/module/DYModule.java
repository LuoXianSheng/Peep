package com.github.lxs.peep.di.module;

import com.github.lxs.peep.di.scope.ActivityScope;
import com.github.lxs.peep.di.scope.FragmentScope;
import com.github.lxs.peep.ui.dy.view.IAllLiveView;
import com.github.lxs.peep.ui.dy.view.IIndexOtherMenuView;
import com.github.lxs.peep.ui.dy.view.IIndexRecomView;
import com.github.lxs.peep.ui.dy.view.ILivePlayView;
import com.github.lxs.peep.ui.dy.view.ITabMenuView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cl on 2017/3/28.
 */

@Module
public class DYModule {

    private IIndexRecomView mIIndexRecomView;
    private ITabMenuView mITabMenuView;
    private IIndexOtherMenuView mIIndexOtherMenuView;
    private IAllLiveView mIAllLiveView;
    private ILivePlayView mILivePlayView;

    public DYModule(IIndexRecomView mIIndexRecomView) {
        this.mIIndexRecomView = mIIndexRecomView;
    }

    public DYModule(ITabMenuView mITabMenuView) {
        this.mITabMenuView = mITabMenuView;
    }

    public DYModule(IIndexOtherMenuView iIndexOtherMenuView) {
        this.mIIndexOtherMenuView = iIndexOtherMenuView;
    }

    public DYModule(IAllLiveView iAllLiveView) {
        this.mIAllLiveView = iAllLiveView;
    }

    public DYModule(ILivePlayView ILivePlayView) {
        mILivePlayView = ILivePlayView;
    }

    @FragmentScope
    @Provides
    public IIndexRecomView providesIRecomView() {
        return mIIndexRecomView;
    }

    @FragmentScope
    @Provides
    public ITabMenuView providesIIndexView() {
        return mITabMenuView;
    }

    @FragmentScope
    @Provides
    public IIndexOtherMenuView providesIOtherMenuView() {
        return mIIndexOtherMenuView;
    }

    @FragmentScope
    @Provides
    public IAllLiveView providesIAllLiveView() {
        return mIAllLiveView;
    }

    @ActivityScope
    @Provides
    public ILivePlayView providesILivePlayView() {
        return mILivePlayView;
    }
}
