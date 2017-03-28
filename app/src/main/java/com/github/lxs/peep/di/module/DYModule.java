package com.github.lxs.peep.di.module;

import com.github.lxs.peep.di.scope.FragmentScope;
import com.github.lxs.peep.ui.dy.view.IIndexView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cl on 2017/3/28.
 */

@Module
public class DYModule {

    private IIndexView mIIndexView;

    public DYModule(IIndexView IIndexView) {
        mIIndexView = IIndexView;
    }

    @FragmentScope
    @Provides
    public IIndexView providesIIndexView() {
        return mIIndexView;
    }
}
