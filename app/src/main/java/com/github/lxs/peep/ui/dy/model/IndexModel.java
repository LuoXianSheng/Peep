package com.github.lxs.peep.ui.dy.model;

import com.github.lxs.peep.bean.dy.HomeCarousel;
import com.github.lxs.peep.bean.dy.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.HomeHotColumn;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
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
 * Created by cl on 2017/3/28.
 */

public class IndexModel {

    public void loadBGAData(OnLoadCompleteListener<List<String>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyApi.class)
                .getCarousel(ParamsMapUtils.getHomeCarousel())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<HttpResponse<List<HomeCarousel>>, Observable<HomeCarousel>>() {
                    @Override
                    public Observable<HomeCarousel> call(HttpResponse<List<HomeCarousel>> listHttpResponse) {
                        return Observable.from(listHttpResponse.getData());
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

    public void loadMenuData(OnLoadCompleteListener<List<String>> listener) {
        HttpUtils.getInstance()
                .getRetofitClinet()
                .builder(ApiManager.DyApi.class)
                .getHomeCateList(ParamsMapUtils.getDefaultParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<HttpResponse<List<IndexCateList>>, Observable<IndexCateList>>() {
                    @Override
                    public Observable<IndexCateList> call(HttpResponse<List<IndexCateList>> listHttpResponse) {
                        return Observable.from(listHttpResponse.getData());
                    }
                })
                .map(IndexCateList::getTitle)
                .buffer(5)
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
                .builder(ApiManager.DyApi.class)
                .getHotColumn(ParamsMapUtils.getDefaultParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HttpResponse::getData)
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
                .builder(ApiManager.DyApi.class)
                .getFaceScoreColumn(ParamsMapUtils.getHomeFaceScoreColumn(offset, limit))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HttpResponse::getData)
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
                .builder(ApiManager.DyApi.class)
                .getHotCate(ParamsMapUtils.getDefaultParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HttpResponse::getData)
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
