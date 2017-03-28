package com.github.lxs.peep.listener;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cl on 2017/3/28.
 */

public abstract class MySubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);
}
