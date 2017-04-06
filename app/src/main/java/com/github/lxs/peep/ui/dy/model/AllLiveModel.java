package com.github.lxs.peep.ui.dy.model;

import com.github.lxs.peep.bean.dy.live.LiveAllList;
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
 * Created by cl on 2017/3/31.
 */

public class AllLiveModel {

    public void loadAllLive(int start, int limit, OnLoadCompleteListener<List<LiveAllList>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyLiveApi.class)
                .getLiveAllList(ParamsMapUtils.getHomeFaceScoreColumn(start, limit))
                .subscribeOn(Schedulers.io())
                .map(DyHttpResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<List<LiveAllList>>() {
                    @Override
                    public void onSuccess(List<LiveAllList> liveAllLists) {
                        listener.onLoadSuccess(liveAllLists);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        listener.onLoadFailed("失败");
                    }
                });
    }
}
