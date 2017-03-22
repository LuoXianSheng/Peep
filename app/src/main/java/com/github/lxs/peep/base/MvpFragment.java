package com.github.lxs.peep.base;

public abstract class MvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment implements BaseView {

    protected P mPresenter;


    protected abstract P createPresenter(BaseView view);

    @Override
    protected void init() {
        mPresenter = createPresenter(this);
        super.init();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null ) {
            mPresenter.onDestroy();
        }
    }
}
