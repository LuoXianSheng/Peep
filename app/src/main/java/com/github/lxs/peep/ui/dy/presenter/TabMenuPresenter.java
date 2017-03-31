package com.github.lxs.peep.ui.dy.presenter;

import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.bean.dy.index.HomeCateList;
import com.github.lxs.peep.bean.dy.live.LiveOtherColumn;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.ui.dy.model.TabMenuModel;
import com.github.lxs.peep.ui.dy.view.ITabMenuView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cl on 2017/3/30.
 */

public class TabMenuPresenter extends BasePresenter<ITabMenuView> {

    private TabMenuModel mTabMenuModel;
    private ITabMenuView mITabMenuView;

    @Inject
    public TabMenuPresenter(ITabMenuView mITabMenuView) {
        super(mITabMenuView);
        this.mITabMenuView = mITabMenuView;
        mTabMenuModel = new TabMenuModel();
    }

    public void loadIndexMenuBase() {
        mTabMenuModel.loadIndexMenuBase(new OnLoadCompleteListener<List<HomeCateList>>() {
            @Override
            public void onLoadSuccess(List<HomeCateList> homeCateLists) {
                mITabMenuView.setTabMenuBaseData(homeCateLists);
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });
    }

    public void loadTabMenuBase() {
        mTabMenuModel.loadTabMenuBase(new OnLoadCompleteListener<List<LiveOtherColumn>>() {
            @Override
            public void onLoadSuccess(List<LiveOtherColumn> liveOtherColumns) {
                mITabMenuView.setTabMenuBaseData(liveOtherColumns);
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });
    }
}
