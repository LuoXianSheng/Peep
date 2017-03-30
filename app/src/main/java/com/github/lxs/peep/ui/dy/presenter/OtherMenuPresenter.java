package com.github.lxs.peep.ui.dy.presenter;

import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.ui.dy.model.OtherMenuModel;
import com.github.lxs.peep.ui.dy.view.IOtherMenuView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cl on 2017/3/30.
 */

public class OtherMenuPresenter extends BasePresenter<IOtherMenuView> {

    private IOtherMenuView mIOtherMenuView;
    private OtherMenuModel mOtherMenuModel;

    @Inject
    public OtherMenuPresenter(IOtherMenuView mIOtherMenuView) {
        super(mIOtherMenuView);
        this.mIOtherMenuView = mIOtherMenuView;
        mOtherMenuModel = new OtherMenuModel();
    }

    public void loadOtherMenuData(String type) {
        mOtherMenuModel.loadMenuBaseData(type, new OnLoadCompleteListener<List<HomeRecommendHotCate>>() {
            @Override
            public void onLoadSuccess(List<HomeRecommendHotCate> homeRecommendHotCates) {
                mView.hideLoading();
                mIOtherMenuView.setOtherCate(homeRecommendHotCates);
            }

            @Override
            public void onLoadFailed(String error) {
                mIOtherMenuView.showError(error);
            }
        });
    }

}
