package com.github.lxs.peep.ui.dy.model;

import com.github.lxs.peep.bean.dy.index.HomeCateList;
import com.github.lxs.peep.bean.dy.live.LiveOtherColumn;
import com.github.lxs.peep.http.ApiManager;
import com.github.lxs.peep.http.HttpResponse;
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

public class TabMenuModel {

    public void loadIndexMenuBase(OnLoadCompleteListener<List<HomeCateList>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyIndexApi.class)
                .getHomeCateList(ParamsMapUtils.getDefaultParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HttpResponse::getData)
                .subscribe(new MySubscriber<List<HomeCateList>>() {
                    @Override
                    public void onSuccess(List<HomeCateList> homeCateLists) {
                        listener.onLoadSuccess(homeCateLists);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        listener.onLoadFailed("失败");
                    }
                });

    }

    public void loadTabMenuBase(OnLoadCompleteListener<List<LiveOtherColumn>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyLiveApi.class)
                .getLiveOtherColumn(ParamsMapUtils.getDefaultParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HttpResponse::getData)
                .subscribe(new MySubscriber<List<LiveOtherColumn>>() {
                    @Override
                    public void onSuccess(List<LiveOtherColumn> liveOtherColumns) {
                        listener.onLoadSuccess(liveOtherColumns);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        listener.onLoadFailed("失败");
                    }
                });

    }
}
