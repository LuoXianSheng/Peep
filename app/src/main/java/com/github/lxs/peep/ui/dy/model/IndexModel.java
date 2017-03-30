package com.github.lxs.peep.ui.dy.model;

import com.github.lxs.peep.bean.dy.IndexCateList;
import com.github.lxs.peep.http.ApiManager;
import com.github.lxs.peep.http.HttpResponse;
import com.github.lxs.peep.http.HttpUtils;
import com.github.lxs.peep.http.ParamsMapUtils;
import com.github.lxs.peep.listener.MySubscriber;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.socks.library.KLog;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cl on 2017/3/30.
 */

public class IndexModel {

    public void loadMenuBaseData(OnLoadCompleteListener<List<IndexCateList>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyApi.class)
                .getHomeCateList(ParamsMapUtils.getDefaultParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HttpResponse::getData)
                .subscribe(new MySubscriber<List<IndexCateList>>() {
                    @Override
                    public void onSuccess(List<IndexCateList> indexCateLists) {
                        listener.onLoadSuccess(indexCateLists);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        listener.onLoadFailed("失败");
                    }
                });

    }
}
