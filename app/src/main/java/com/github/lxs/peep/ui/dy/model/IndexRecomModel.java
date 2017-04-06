package com.github.lxs.peep.ui.dy.model;

import com.github.lxs.peep.bean.dy.index.HomeCarousel;
import com.github.lxs.peep.bean.dy.index.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.index.HomeHotColumn;
import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;
import com.github.lxs.peep.http.ApiManager;
import com.github.lxs.peep.http.response.DyHttpResponse;
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
 * Created by cl on 2017/3/28.
 */

public class IndexRecomModel {

    public void loadBGAData(OnLoadCompleteListener<List<String>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyIndexApi.class)
                .getCarousel(ParamsMapUtils.getHomeCarousel())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<DyHttpResponse<List<HomeCarousel>>, Observable<HomeCarousel>>() {
                    @Override
                    public Observable<HomeCarousel> call(DyHttpResponse<List<HomeCarousel>> listDyHttpResponse) {
                        return Observable.from(listDyHttpResponse.getData());
                    }
                })
                .map(HomeCarousel::getPic_url)
                .buffer(10)
                .subscribe(new MySubscriber<List<String>>() {
                    @Override
                    public void onSuccess(List<String> strings) {
                        listener.onLoadSuccess(strings);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        listener.onLoadFailed("失败");
                    }

                });
    }


    public void loadHotColumns(OnLoadCompleteListener<List<HomeHotColumn>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyIndexApi.class)
                .getHotColumn(ParamsMapUtils.getDefaultParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(DyHttpResponse::getData)
                .subscribe(new MySubscriber<List<HomeHotColumn>>() {
                    @Override
                    public void onSuccess(List<HomeHotColumn> homeHotColumns) {
                        listener.onLoadSuccess(homeHotColumns);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        listener.onLoadFailed("失败");
                    }
                });
    }

    public void loadFaceScoreColumns(int offset, int limit, OnLoadCompleteListener<List<HomeFaceScoreColumn>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyIndexApi.class)
                .getFaceScoreColumn(ParamsMapUtils.getHomeFaceScoreColumn(offset, limit))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(DyHttpResponse::getData)
                .subscribe(new MySubscriber<List<HomeFaceScoreColumn>>() {
                    @Override
                    public void onSuccess(List<HomeFaceScoreColumn> columns) {
                        listener.onLoadSuccess(columns);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                        listener.onLoadFailed("失败");
                    }
                });
    }

    public void loadOtherAllColumns(OnLoadCompleteListener<List<HomeRecommendHotCate>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyIndexApi.class)
                .getHotCate(ParamsMapUtils.getDefaultParams())
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
