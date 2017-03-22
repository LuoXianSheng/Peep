package com.github.lxs.peep.base;

public interface OnLoadCompleteListener<T> {

    void onLoadSussess(T t);

    void onLoadFailed(String error);

}
