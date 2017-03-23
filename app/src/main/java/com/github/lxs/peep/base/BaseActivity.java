package com.github.lxs.peep.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutRes());
        ButterKnife.bind(this);
        mContext = this;
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
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
