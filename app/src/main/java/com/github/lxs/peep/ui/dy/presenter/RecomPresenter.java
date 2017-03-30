package com.github.lxs.peep.ui.dy.presenter;

import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.bean.dy.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.HomeHotColumn;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.ui.dy.model.RecomModel;
import com.github.lxs.peep.ui.dy.view.IRecomView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cl on 2017/3/28.
 */

public class RecomPresenter extends BasePresenter<IRecomView> {

    private RecomModel mRecomModel;
    private IRecomView mIRecomView;

    @Inject
    public RecomPresenter(IRecomView mIRecomView) {
        super(mIRecomView);
        this.mIRecomView = mIRecomView;
        mRecomModel = new RecomModel();
    }

    public void loadBGAData() {
        mRecomModel.loadBGAData(new OnLoadCompleteListener<List<String>>() {
            @Override
            public void onLoadSuccess(List<String> strings) {
                mIRecomView.setBGAData(strings);
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
            }
        });
    }


    public void loadHotColumns() {
        mRecomModel.loadHotColumns(new OnLoadCompleteListener<List<HomeHotColumn>>() {
            @Override
            public void onLoadSuccess(List<HomeHotColumn> homeHotColumns) {
                mIRecomView.setHotColumns(homeHotColumns);
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
            }
        });
    }

    public void loadFaceScoreColumns(int offset, int limit) {
        mRecomModel.loadFaceScoreColumns(offset, limit, new OnLoadCompleteListener<List<HomeFaceScoreColumn>>() {
            @Override
            public void onLoadSuccess(List<HomeFaceScoreColumn> columns) {
                mIRecomView.setFaceScoreColumns(columns);
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
            }
        });
    }

    public void loadOtherAllColumns() {
        mRecomModel.loadOtherAllColumns(new OnLoadCompleteListener<List<HomeRecommendHotCate>>() {
            @Override
            public void onLoadSuccess(List<HomeRecommendHotCate> homeRecommendHotCates) {
                mIRecomView.setOtherAllColumns(homeRecommendHotCates);
                mView.hideLoading();
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
                mIRecomView.showError(error);
            }
        });
    }
}
