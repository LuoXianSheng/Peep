package com.github.lxs.peep.listener;

public interface OnLoadCompleteListener<T> {

    void onLoadSussess(T t);

    void onLoadFailed(String error);

}
