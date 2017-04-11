package com.github.lxs.peep.ui.girl.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.andview.refreshview.XRefreshView;
import com.github.lxs.peep.R;
import com.github.lxs.peep.base.MvpFragment;
import com.github.lxs.peep.bean.girl.GirlInfo;
import com.github.lxs.peep.di.component.DaggerGirlFragmentComponent;
import com.github.lxs.peep.di.module.GirlModel;
import com.github.lxs.peep.ui.girl.presenter.GirlPresenter;
import com.github.lxs.peep.ui.girl.ui.adapter.Girladapter;
import com.github.lxs.peep.ui.girl.view.IGirlView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/24.
 */

public class GirlFragment extends MvpFragment<IGirlView, GirlPresenter> implements IGirlView {


    @BindView(R.id.refreshView)
    XRefreshView mRefreshView;
    @BindView(R.id.gridView)
    GridView mGridView;

    private int page = 0, limit = 20;
    private String tag1 = "美女", tag2 = "全部";

    private Girladapter mGirladapter;
    private ArrayList<GirlInfo> mLists;

    @Inject
    GirlPresenter mPresenter;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_girl_gridview, null);
    }

    @Override
    protected void initViews() {
        initRefresh();

        mLists = new ArrayList<>();
        mGirladapter = new Girladapter(mContext, mLists);
        mGridView.setAdapter(mGirladapter);

        mGridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(mActivity, PhotoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("mLists", mLists);
            intent.putExtras(bundle);
            intent.putExtra("pos", position);
            startActivity(intent);
            mActivity.overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
        });
    }

    private void initRefresh() {
        mRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                page = 0;
                mHandler.postDelayed(() -> initData(), 500);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                mHandler.postDelayed(() -> mPresenter.loadMoreGirl(++page, limit, tag1, tag2), 500);
            }
        });
        mRefreshView.setPinnedTime(1000);
        mRefreshView.setPullLoadEnable(true);
        mRefreshView.setPullRefreshEnable(true);
        mRefreshView.setPinnedContent(true);
        mRefreshView.setSilenceLoadMore();
        mRefreshView.setPreLoadCount(4);
    }

    @Override
    protected void initData() {
        mPresenter.loadGirl(0, limit, tag1, tag2);
    }

    @Override
    public void setDatas(List<GirlInfo> mDatas) {
        mActivity.runOnUiThread(() -> {
            this.mLists.clear();
            this.mLists.addAll(mDatas);
            mGirladapter.notifyDataSetChanged();
            mRefreshView.stopRefresh();
        });
    }

    @Override
    public void loadMore(List<GirlInfo> mDatas) {
        mActivity.runOnUiThread(() -> {
            this.mLists.addAll(mDatas);
            mGirladapter.notifyDataSetChanged();
            mRefreshView.stopLoadMore();
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mActivity.runOnUiThread(() -> super.hideLoadView());
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected GirlPresenter createPresenter() {
        DaggerGirlFragmentComponent.builder().girlModel(new GirlModel(this)).build().inject(this);
        return mPresenter;
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        if (isVisibleToUser) {
            super.onVisible();
        } else {
            super.onInvisible();
        }
    }
}
