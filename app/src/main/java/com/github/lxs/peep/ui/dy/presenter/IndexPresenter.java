package com.github.lxs.peep.ui.dy.presenter;

import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.bean.dy.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.HomeHotColumn;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.ui.dy.model.IndexModel;
import com.github.lxs.peep.ui.dy.view.IIndexView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cl on 2017/3/28.
 */

public class IndexPresenter extends BasePresenter<IIndexView> {

    private IndexModel mIndexModel;
    private IIndexView mIIndexView;

    @Inject
    public IndexPresenter(IIndexView mIIndexView) {
        super(mIIndexView);
        this.mIIndexView = mIIndexView;
        mIndexModel = new IndexModel();
    }

    public void loadBGAData() {
        mView.showLoading();
        mIndexModel.loadBGAData(new OnLoadCompleteListener<List<String>>() {
            @Override
            public void onLoadSuccess(List<String> strings) {
                mIIndexView.setBGAData(strings);
                mView.hideLoading();
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });
    }

    public void loadMenuData() {
        mIndexModel.loadMenuData(new OnLoadCompleteListener<List<String>>() {
            @Override
            public void onLoadSuccess(List<String> strings) {
                mIIndexView.setMenuData(strings);
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });
    }


    public void loadHotColumns() {
        mIndexModel.loadHotColumns(new OnLoadCompleteListener<List<HomeHotColumn>>() {
            @Override
            public void onLoadSuccess(List<HomeHotColumn> homeHotColumns) {
                mIIndexView.setHotColumns(homeHotColumns);
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });
    }

    public void loadFaceScoreColumns(int offset, int limit) {
        mIndexModel.loadFaceScoreColumns(offset, limit, new OnLoadCompleteListener<List<HomeFaceScoreColumn>>() {
            @Override
            public void onLoadSuccess(List<HomeFaceScoreColumn> columns) {
                mIIndexView.setFaceScoreColumns(columns);
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });
    }

    public void loadOtherAllColumns() {
        mIndexModel.loadOtherAllColumns(new OnLoadCompleteListener<List<HomeRecommendHotCate>>() {
            @Override
            public void onLoadSuccess(List<HomeRecommendHotCate> homeRecommendHotCates) {
                mIIndexView.setOtherAllColumns(homeRecommendHotCates);
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });
    }

    public void loadTest() {

    }
}
