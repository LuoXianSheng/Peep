package com.github.lxs.peep.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.lxs.peep.R;

import butterknife.ButterKnife;

public abstract class MvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment {

    protected P mPresenter;
    protected boolean isVisible;
    protected Handler mHandler = new Handler();
    private Runnable mRunnable;

    private View loadStatusView;
    private ViewGroup viewGroup;
    private LinearLayout loadingLayout, loadErrorLayout;

    private int delayedTime = 0;
    private boolean isLoadData = false;

    public void setDelayedTime(int delayedTime) {
        this.delayedTime = delayedTime;
    }

    protected abstract P createPresenter();

    @Override
    protected void init() {
        mPresenter = createPresenter();
        if (delayedTime == 0)
            super.init();
    }

    @Override
    protected void initLoadStatusView() {
        if (mRootView instanceof LinearLayout) {
            viewGroup = (LinearLayout) mRootView;
        }
        if (viewGroup == null) return;
        loadStatusView = LayoutInflater.from(mContext).inflate(R.layout.layout_loading_status, null);
        loadingLayout = ButterKnife.findById(loadStatusView, R.id.loading_layout);
        loadErrorLayout = ButterKnife.findById(loadStatusView, R.id.load_error_layout);
        viewGroup.addView(loadStatusView);
        ButterKnife.findById(loadStatusView, R.id.btn_reset).setOnClickListener(v -> {
            hideLoadErrorView();
            showLoadView();
            MvpFragment.super.initD();
        });
    }

    protected void showLoadView() {
        ImageView img = ButterKnife.findById(loadStatusView, R.id.loading_view);
        AnimationDrawable mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        mAnimationDrawable.start();
        loadingLayout.setVisibility(View.VISIBLE);
    }

    protected void hideLoadView() {
        loadingLayout.setVisibility(View.GONE);
    }

    protected void hideLoadErrorView() {
        loadErrorLayout.setVisibility(View.GONE);
    }

    protected void showLoadErrorView() {
        loadErrorLayout.setVisibility(View.VISIBLE);
    }

    protected void removeLoadStatusView() {
        if (loadStatusView == null) return;
        viewGroup.removeView(loadStatusView);
        loadStatusView = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        if (isLoadData || delayedTime == 0) return;
        showLoadView();
        mRunnable = () -> {
            isLoadData = true;
            super.init();
        };
        mHandler.postDelayed(mRunnable, delayedTime);
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
        if (isLoadData || delayedTime == 0) return;
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
