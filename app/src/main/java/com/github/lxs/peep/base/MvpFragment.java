package com.github.lxs.peep.base;

public abstract class MvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment {

    protected P mPresenter;


    protected abstract P createPresenter();

    @Override
    protected void init() {
        mPresenter = createPresenter();
        super.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null ) {
            mPresenter.onDestroy();
        }
    }
}
