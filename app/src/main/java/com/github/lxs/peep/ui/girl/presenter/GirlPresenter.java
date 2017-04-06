package com.github.lxs.peep.ui.girl.presenter;

import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.bean.girl.GirlInfo;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.ui.girl.model.GirlModel;
import com.github.lxs.peep.ui.girl.view.IGirlView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cl on 2017/4/6.
 */

public class GirlPresenter extends BasePresenter<IGirlView> {

    private GirlModel mGirlModel;
    private IGirlView mIGirlView;

    @Inject
    public GirlPresenter(IGirlView iGirlView) {
        super(iGirlView);
        this.mIGirlView = iGirlView;
        mGirlModel = new GirlModel();
    }

    public void loadGirl(int index, int limit, String tag1, String tag2) {
        mGirlModel.loadGirlData(index, limit, tag1, tag2, new OnLoadCompleteListener<List<GirlInfo>>() {
            @Override
            public void onLoadSuccess(List<GirlInfo> girlInfos) {
                mView.hideLoading();
                mIGirlView.setDatas(girlInfos);
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
            }
        });
    }

    public void loadMoreGirl(int index, int limit, String tag1, String tag2) {
        KLog.e(index);
        mGirlModel.loadGirlData(index, limit, tag1, tag2, new OnLoadCompleteListener<List<GirlInfo>>() {
            @Override
            public void onLoadSuccess(List<GirlInfo> girlInfos) {
                mIGirlView.loadMore(girlInfos);
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
            }
        });
    }
}
