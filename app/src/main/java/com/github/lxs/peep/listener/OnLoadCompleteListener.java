package com.github.lxs.peep.listener;

public interface OnLoadCompleteListener<T> {

    void onLoadSuccess(T t);

    void onLoadFailed(String error);

}
