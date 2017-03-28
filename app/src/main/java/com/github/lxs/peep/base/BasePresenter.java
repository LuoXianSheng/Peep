package com.github.lxs.peep.base;

/**
 * Created by Long
 * on 2016/9/2.
 */
public class BasePresenter<V> {

    protected V mView;

    public BasePresenter(V v) {
        mView = v;
    }

    public void onDestroy() {
        mView = null;
    }

}
