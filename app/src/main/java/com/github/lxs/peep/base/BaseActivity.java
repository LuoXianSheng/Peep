package com.github.lxs.peep.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.lxs.peep.App;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected Activity mActivity;
    private Unbinder mUnbinder;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(setLayoutRes());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        init();
    }

    protected void init() {
        initViews();
        initData();
    }

    protected abstract int setLayoutRes();

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
        mToast = Toast.makeText(mContext, msg, duration);
        mToast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();

        //会自动检测，可以不写
//        RefWatcher refWatcher = App.getRefWatcher(this);
//        refWatcher.watch(this);
    }

}
