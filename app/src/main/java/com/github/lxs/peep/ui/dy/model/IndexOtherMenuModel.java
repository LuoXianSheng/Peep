package com.github.lxs.peep.ui.dy.model;

import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;
import com.github.lxs.peep.http.ApiManager;
import com.github.lxs.peep.http.response.DyHttpResponse;
import com.github.lxs.peep.http.HttpUtils;
import com.github.lxs.peep.http.ParamsMapUtils;
import com.github.lxs.peep.listener.MySubscriber;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.socks.library.KLog;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cl on 2017/3/30.
 */

public class IndexOtherMenuModel {

    public void loadMenuBaseData(String type, OnLoadCompleteListener<List<HomeRecommendHotCate>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyIndexApi.class)
                .getHomeCate(ParamsMapUtils.getHomeCate(type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(DyHttpResponse::getData)
                .subscribe(new MySubscriber<List<HomeRecommendHotCate>>() {
                    @Override
                    public void onSuccess(List<HomeRecommendHotCate> homeRecommendHotCates) {
                        listener.onLoadSuccess(homeRecommendHotCates);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        listener.onLoadFailed("失败");
                    }
                });
    }
}
