package com.github.lxs.peep.ui.dy.presenter;

import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.ui.dy.model.IndexOtherMenuModel;
import com.github.lxs.peep.ui.dy.view.IIndexOtherMenuView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cl on 2017/3/30.
 */

public class IndexOtherMenuPresenter extends BasePresenter<IIndexOtherMenuView> {

    private IIndexOtherMenuView mIIndexOtherMenuView;
    private IndexOtherMenuModel mIndexOtherMenuModel;

    @Inject
    public IndexOtherMenuPresenter(IIndexOtherMenuView mIIndexOtherMenuView) {
        super(mIIndexOtherMenuView);
        this.mIIndexOtherMenuView = mIIndexOtherMenuView;
        mIndexOtherMenuModel = new IndexOtherMenuModel();
    }

    public void loadOtherMenuData(String type) {
        mIndexOtherMenuModel.loadMenuBaseData(type, new OnLoadCompleteListener<List<HomeRecommendHotCate>>() {
            @Override
            public void onLoadSuccess(List<HomeRecommendHotCate> homeRecommendHotCates) {
                mView.hideLoading();
                mIIndexOtherMenuView.setOtherCate(homeRecommendHotCates);
            }

            @Override
            public void onLoadFailed(String error) {
                mIIndexOtherMenuView.showError(error);
            }
        });
    }

}
