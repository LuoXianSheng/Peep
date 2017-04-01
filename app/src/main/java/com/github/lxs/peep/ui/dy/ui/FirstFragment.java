package com.github.lxs.peep.ui.dy.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class FirstFragment extends BaseFragment {

    @BindView(R.id.rootLayout)
    LinearLayout mRootLayout;
    @BindView(R.id.edt_content)
    EditText edtContent;

    private boolean isOpenInput;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.live_detail_first_fragment, null);
    }

    @Override
    public void initViews() {
        edtContent.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showSoftInputView();
            } else {
                hideSoftInputView();
            }
        });

        mRootLayout.setOnTouchListener((v, event) -> {
            clearFocus();
            return false;
        });
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }


    @OnClick(R.id.edt_content)
    public void edtContentClick() {
        edtContent.requestFocus();
    }

    @OnClick(R.id.img_send)
    public void btnSendClick() {
        edtContent.requestFocus();
        edtContent.setText("");
    }

    public void clearFocus() {
        if (edtContent == null)
            return;
        edtContent.clearFocus();
    }

    public void hideSoftInputView() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isOpenInput) {
            imm.hideSoftInputFromWindow(edtContent.getWindowToken(), 0); // 强制隐藏键盘
            isOpenInput = false;
        }
    }

    public void showSoftInputView() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtContent, InputMethodManager.SHOW_FORCED);// 总是显示输入法
        isOpenInput = true;
    }

    public boolean getIsOpenImput() {
        return isOpenInput;
    }
}
