package com.github.lxs.peep.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.lxs.peep.R;
import com.socks.library.KLog;

import butterknife.ButterKnife;

public abstract class MvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment {

    protected P mPresenter;
    protected boolean isVisible;
    protected Handler mHandler = new Handler();
    private Runnable mRunnable;

    private View loadView;
    private LinearLayout loadingLayout, loadErrorLayout;

    private boolean isLoadData = false;
    private AnimationDrawable mAnimationDrawable;

    protected abstract P createPresenter();


    protected int delayedTime = 0;

    public void setDelayedTime(int delayedTime) {
        this.delayedTime = delayedTime;
    }

    @Override
    protected void init() {
        mPresenter = createPresenter();
        initLoadStatusView();
    }

    protected void initLoadStatusView() {
        loadView = ButterKnife.findById(mRootView, R.id.load_layout);
        if (loadView == null) {
            super.init();
            return;
        }
        loadingLayout = ButterKnife.findById(mRootView, R.id.loading_layout);
        loadErrorLayout = ButterKnife.findById(mRootView, R.id.load_error_layout);
        ButterKnife.findById(mRootView, R.id.btn_reset).setOnClickListener(v -> {
            hideLoadErrorView();
            showLoadView();
            mHandler.postDelayed(MvpFragment.super::initD, 500);
        });
    }

    protected void showLoadView() {
        if (loadView == null) return;
        loadingLayout.setVisibility(View.VISIBLE);
        ImageView img = ButterKnife.findById(loadView, R.id.loading_view);
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        mAnimationDrawable.start();
    }

    protected void hideLoadingView() {
        if (loadView == null) return;
        loadingLayout.setVisibility(View.GONE);
        mAnimationDrawable.stop();
    }

    protected void hideLoadErrorView() {
        if (loadView == null) return;
        loadErrorLayout.setVisibility(View.GONE);
    }

    protected void showLoadErrorView() {
        if (loadView == null) return;
        loadErrorLayout.setVisibility(View.VISIBLE);
    }

    protected void hideLoadView() {
        if (loadView == null) return;
        if (loadView.getVisibility() == View.GONE) return;
        loadView.setVisibility(View.GONE);
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning())
            mAnimationDrawable.stop();
    }


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if (getUserVisibleHint()) {
//            isVisible = true;
//            onVisible();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }

//    /**
//     * 可见
//     */
//    protected void onVisible() {
//
//    }
//
//    /**
//     * 不可见
//     */
//    protected void onInvisible() {
//
//    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        if (isVisibleToUser) {
            if (isLoadData) return;
            showLoadView();
            mRunnable = () -> {
                isLoadData = true;
                super.init();
            };
            mHandler.postDelayed(mRunnable, delayedTime);
        } else {
            if (isLoadData) return;
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
