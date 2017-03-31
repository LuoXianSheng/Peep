package com.github.lxs.peep.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.lxs.peep.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected Context mContext;
    protected Activity mActivity;
    private Unbinder unbinder;
    private Toast mToast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initRootView(inflater, container);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        mActivity = getActivity();
        init();
    }

    protected void init() {
        initViews();
        initData();
    }

    protected void initV() {
        initViews();
    }

    protected void initD() {
        initViews();
    }

    protected abstract View initRootView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initViews();

    protected abstract void initData();

    protected void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    protected void showLongToast(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    private void showToast(String msg, int duration) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(getActivity(), msg, duration);
        mToast.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = App.sRefWatcher;
//        refWatcher.watch(this);
    }
}
