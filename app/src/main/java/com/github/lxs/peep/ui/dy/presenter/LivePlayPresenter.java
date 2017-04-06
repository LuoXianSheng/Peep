package com.github.lxs.peep.ui.dy.presenter;

import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.bean.dy.OldLiveVideoInfo;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.ui.dy.model.LivePlayModel;
import com.github.lxs.peep.ui.dy.view.ILivePlayView;
import com.socks.library.KLog;

import javax.inject.Inject;

/**
 * Created by cl on 2017/4/1.
 */

public class LivePlayPresenter extends BasePresenter<ILivePlayView> {

    private LivePlayModel mLivePlayModel;
    private ILivePlayView mILivePlayView;

    @Inject
    public LivePlayPresenter(ILivePlayView iLivePlayView) {
        super(iLivePlayView);
        mILivePlayView = iLivePlayView;
        mLivePlayModel = new LivePlayModel();
    }

    public void loadPlayInfo(String roomId) {
        KLog.e("开始加载数据---" + roomId);
        mLivePlayModel.loadPlayInfo(roomId, new OnLoadCompleteListener<OldLiveVideoInfo>() {
            @Override
            public void onLoadSuccess(OldLiveVideoInfo oldLiveVideoInfo) {
                mILivePlayView.setPlayInfo(oldLiveVideoInfo);
            }

            @Override
            public void onLoadFailed(String error) {
                KLog.e(error);
                mILivePlayView.showError(error);
            }
        });
    }
}
