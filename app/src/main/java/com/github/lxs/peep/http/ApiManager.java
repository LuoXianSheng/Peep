package com.github.lxs.peep.http;

import com.github.lxs.peep.bean.Top250;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiManager {

    @GET("top250")
    Observable<Top250> getTop250Movie(@Query("start") int start, @Query("count") int count);
}
