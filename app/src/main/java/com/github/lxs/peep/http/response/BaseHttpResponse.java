package com.github.lxs.peep.http.response;

/**
 * Created by cl on 2017/4/6.
 */

public class BaseHttpResponse<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
