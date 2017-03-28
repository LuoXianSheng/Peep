package com.github.lxs.peep.base;

import android.os.Handler;

public abstract class BaseDelayedLoadFragment extends MvpFragment {

    protected boolean isVisible;
    protected Handler mHandler = new Handler();

    private int delayedTime = 1500;

    public void setDelayedTime(int delayedTime) {
        this.delayedTime = delayedTime;
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
        mRunnable = new Runnable() {

            @Override
            public void run() {
                loadData();
            }
        };
        mHandler.postDelayed(mRunnable, delayedTime);
    }

    private Runnable mRunnable;

    /**
     * 不可见
     */
    protected void onInvisible() {
        mHandler.removeCallbacks(mRunnable);
    }

    protected abstract void loadData();
}
