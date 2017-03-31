package com.github.lxs.peep.ui.dy.presenter;

import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.bean.dy.index.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.index.HomeHotColumn;
import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.ui.dy.model.IndexRecomModel;
import com.github.lxs.peep.ui.dy.view.IIndexRecomView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cl on 2017/3/28.
 */

public class IndexRecomPresenter extends BasePresenter<IIndexRecomView> {

    private IndexRecomModel mIndexRecomModel;
    private IIndexRecomView mIIndexRecomView;

    @Inject
    public IndexRecomPresenter(IIndexRecomView mIIndexRecomView) {
        super(mIIndexRecomView);
        this.mIIndexRecomView = mIIndexRecomView;
        mIndexRecomModel = new IndexRecomModel();
    }

    public void loadBGAData() {
        mIndexRecomModel.loadBGAData(new OnLoadCompleteListener<List<String>>() {
            @Override
            public void onLoadSuccess(List<String> strings) {
                mIIndexRecomView.setBGAData(strings);
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
            }
        });
    }


    public void loadHotColumns() {
        mIndexRecomModel.loadHotColumns(new OnLoadCompleteListener<List<HomeHotColumn>>() {
            @Override
            public void onLoadSuccess(List<HomeHotColumn> homeHotColumns) {
                mIIndexRecomView.setHotColumns(homeHotColumns);
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
            }
        });
    }

    public void loadFaceScoreColumns(int offset, int limit) {
        mIndexRecomModel.loadFaceScoreColumns(offset, limit, new OnLoadCompleteListener<List<HomeFaceScoreColumn>>() {
            @Override
            public void onLoadSuccess(List<HomeFaceScoreColumn> columns) {
                mIIndexRecomView.setFaceScoreColumns(columns);
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
            }
        });
    }

    public void loadOtherAllColumns() {
        mIndexRecomModel.loadOtherAllColumns(new OnLoadCompleteListener<List<HomeRecommendHotCate>>() {
            @Override
            public void onLoadSuccess(List<HomeRecommendHotCate> homeRecommendHotCates) {
                mIIndexRecomView.setOtherAllColumns(homeRecommendHotCates);
                mView.hideLoading();
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
                mIIndexRecomView.showError(error);
            }
        });
    }
}
