package com.github.lxs.peep.ui.dy.presenter;

import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.bean.dy.live.LiveAllList;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.ui.dy.model.AllLiveModel;
import com.github.lxs.peep.ui.dy.view.IAllLiveView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cl on 2017/3/31.
 */

public class AllLivePresenter extends BasePresenter<IAllLiveView> {

    private AllLiveModel mAllLiveModel;
    private IAllLiveView mIAllLiveView;

    @Inject
    public AllLivePresenter(IAllLiveView iAllLiveView) {
        super(iAllLiveView);
        this.mIAllLiveView = iAllLiveView;
        mAllLiveModel = new AllLiveModel();
    }

    public void loadAllLiveData(int start, int limit, boolean isLoadMore) {
        mAllLiveModel.loadAllLive(start, limit, new OnLoadCompleteListener<List<LiveAllList>>() {
            @Override
            public void onLoadSuccess(List<LiveAllList> liveAllLists) {
                mView.hideLoading();
                if (isLoadMore) mIAllLiveView.loadMore(liveAllLists);
                else mIAllLiveView.setLiveData(liveAllLists);
            }

            @Override
            public void onLoadFailed(String error) {
                mView.hideLoading();
            }
        });
    }
}
