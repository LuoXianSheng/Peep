package com.github.lxs.peep.ui.dy.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.andview.refreshview.XRefreshView;
import com.github.lxs.peep.R;
import com.github.lxs.peep.base.MvpFragment;
import com.github.lxs.peep.bean.dy.live.LiveAllList;
import com.github.lxs.peep.di.component.DaggerDYFragmentComponent;
import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.ui.dy.presenter.AllLivePresenter;
import com.github.lxs.peep.ui.dy.ui.adapter.AllLiveAdapter;
import com.github.lxs.peep.ui.dy.view.IAllLiveView;
import com.socks.library.KLog;

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
    @BindView(R.id.gridView)
    GridView mGridView;

    @Inject
    AllLivePresenter mPresenter;

    private AllLiveAdapter<LiveAllList> mAdapter;

    private int start = 0;
    private int limit = 20;
    private ArrayList<LiveAllList> mLists;

    public AllLiveFragment() {
    }

    public AllLiveFragment(int delayedTime) {
        super.setDelayedTime(delayedTime);
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_refresh_gridview, null);
    }

    @Override
    protected void initViews() {
        initRefresh();

        mLists = new ArrayList<>();
        mAdapter = new AllLiveAdapter<>(mLists, mContext);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener((parent, view, position, id) -> {
            LiveAllList item = mLists.get(position);
            Intent intent = new Intent();
            if (item.getCate_id() == 201)
                intent.setClass(mContext, PhoneLivePlayActivity.class);
            else
                intent.setClass(mContext, PcLivePlayActivity.class);
            intent.putExtra("roomName", item.getRoom_name());
            intent.putExtra("roomId", item.getRoom_id());
            mContext.startActivity(intent);
        });
    }

    private void initRefresh() {
//        mRefreshView.setCustomHeaderView(new RainDropHeader(mContext));
//        mRefreshView.setCustomFooterView(new );
        mRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                start = 0;
                mHandler.postDelayed(() -> initData(), 500);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                mHandler.postDelayed(() -> mPresenter.loadAllLiveData(start += limit, limit, true), 500);
            }
        });

        mRefreshView.setPinnedTime(1000);
        mRefreshView.setPullLoadEnable(true);
        mRefreshView.setPullRefreshEnable(true);
        mRefreshView.setMoveForHorizontal(true);
        mRefreshView.setPinnedContent(true);
        mRefreshView.setSilenceLoadMore();
        mRefreshView.setPreLoadCount(3);
    }

    @Override
    protected void initData() {
        mPresenter.loadAllLiveData(0, limit, false);
    }

    @Override
    public void setLiveData(List<LiveAllList> mLists) {
        this.mLists.clear();
        this.mLists.addAll(mLists);
//        mAdapter.refreshData(this.mLists);
        mAdapter.notifyDataSetChanged();
        mRefreshView.stopRefresh();
    }

    @Override
    public void loadMore(List<LiveAllList> mLists) {
        this.mLists.addAll(mLists);
//        mAdapter.loadMore(this.mLists);
        mAdapter.notifyDataSetChanged();
        mRefreshView.stopLoadMore();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        hideLoadView();
    }

    @Override
    public void showError(String error) {
        showToast(error);
        showLoadErrorView();
        mRefreshView.stopRefresh();
    }

    @Override
    protected AllLivePresenter createPresenter() {
        DaggerDYFragmentComponent.builder().dYModule(new DYModule(this)).build().inject(this);
        return mPresenter;
    }
}
