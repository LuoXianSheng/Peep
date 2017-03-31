package com.github.lxs.peep.ui.dy.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.github.lxs.peep.R;
import com.github.lxs.peep.base.MvpFragment;
import com.github.lxs.peep.bean.dy.live.LiveAllList;
import com.github.lxs.peep.di.component.DaggerDYComponent;
import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.ui.dy.presenter.AllLivePresenter;
import com.github.lxs.peep.ui.dy.ui.adapter.AllLiveAdapter;
import com.github.lxs.peep.ui.dy.view.IAllLiveView;
import com.github.lxs.peep.widget.refresh.SmileyHeaderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/24.
 */

public class AllLiveFragment extends MvpFragment<IAllLiveView, AllLivePresenter> implements IAllLiveView {

    @BindView(R.id.refreshView)
    XRefreshView mRefreshView;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Inject
    AllLivePresenter mPresenter;

    private AllLiveAdapter mAdapter;

    private int start = 0;
    private int limit = 20;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_refresh_recyclerview, null);
    }

    @Override
    protected void initViews() {
        initRefresh();

        mAdapter = new AllLiveAdapter(mContext, new ArrayList<>());
        mRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerview.setAdapter(mAdapter);
    }

    private void initRefresh() {
        mRefreshView.setCustomHeaderView(new SmileyHeaderView(mContext));
        mRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                mHandler.postDelayed(() -> initData(), 500);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                mHandler.postDelayed(() -> mPresenter.loadAllLiveData(start += limit, limit), 500);
            }
        });

        mRefreshView.setPinnedTime(1000);
        mRefreshView.setPullLoadEnable(true);
        mRefreshView.setPullRefreshEnable(true);
        mRefreshView.setMoveForHorizontal(true);
        mRefreshView.setPinnedContent(true);
    }

    @Override
    protected void initData() {
        mPresenter.loadAllLiveData(0, limit);
    }

    @Override
    public void setLiveData(List<LiveAllList> mLists) {
        mAdapter.refreshData(mLists);
    }

    @Override
    public void loadMore(List<LiveAllList> mLists) {
        mAdapter.loadMroe(mLists);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        hideLoadView();
        mRefreshView.stopRefresh();
    }

    @Override
    public void showError(String error) {
        showToast(error);
        showLoadErrorView();
        mRefreshView.stopRefresh();
    }

    @Override
    protected AllLivePresenter createPresenter() {
        DaggerDYComponent.builder().dYModule(new DYModule(this)).build().inject(this);
        return mPresenter;
    }

}
