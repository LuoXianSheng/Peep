package com.github.lxs.peep.di.component;

import com.github.lxs.peep.di.module.GirlModel;
import com.github.lxs.peep.di.scope.FragmentScope;
import com.github.lxs.peep.ui.girl.ui.GirlFragment;

import dagger.Component;

/**
 * Created by cl on 2017/4/6.
 */

@FragmentScope
@Component(modules = GirlModel.class)
public interface GirlFragmentComponent {

    void inject(GirlFragment girlFragment);
}
