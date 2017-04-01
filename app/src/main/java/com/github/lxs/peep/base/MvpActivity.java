package com.github.lxs.peep.base;

public abstract class MvpActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity {

    protected P mPresenter;


    protected abstract P createPresenter();

    @Override
    protected void init() {
        mPresenter = createPresenter();
        super.init();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mPresenter != null) {
//            mPresenter.onDestroy();
//        }
//    }
}
