package com.github.lxs.peep.ui.dy.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/24.
 */

public class DYFragment extends BaseFragment implements OnTabSelectListener {

    @BindView(R.id.dy_tab)
    SegmentTabLayout mTab;
    @BindView(R.id.dy_layFrame)
    FrameLayout mLayFrame;

    private String[] titles = {"首页", "直播"};
    private SparseArray<Fragment> mSparseArray;
    private FragmentManager mManager;


    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy, null);
    }

    @Override
    protected void init() {
        mSparseArray = new SparseArray<>();
        super.init();
    }

    @Override
    protected void initViews() {
        mManager = getFragmentManager();
        mManager.beginTransaction().add(R.id.dy_layFrame, buildFragment(0)).commitAllowingStateLoss();
        mTab.setTabData(titles);
        mTab.setOnTabSelectListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onTabSelect(int position) {
        FragmentTransaction transaction = mManager.beginTransaction();
        if (mSparseArray.get(position, null) == null) {
            transaction.hide(mSparseArray.get(0)).add(R.id.dy_layFrame, buildFragment(position));
        } else transaction.hide(mSparseArray.get(position == 0 ? 1 : 0)).show(mSparseArray.get(position));
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabReselect(int position) {

    }

    private Fragment buildFragment(int position) {
        if (position == 0)
            mSparseArray.append(position, new IndexFragment());
        else if (position == 1)
            mSparseArray.append(position, new LiveFragment());
        else return null;
        return mSparseArray.get(position);
    }
}
