package com.github.lxs.peep.ui.dy.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BaseFragment;
import com.github.lxs.peep.ui.dy.ui.adapter.DYFragmentAdapter;
import com.github.lxs.peep.ui.wb.ui.TwoFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/24.
 */

public class LiveFragment extends BaseFragment {

    @BindView(R.id.dy_live_tab)
    SlidingTabLayout mLiveTab;
    @BindView(R.id.dy_live_index_viewpager)
    ViewPager mLiveIndexViewpager;

    private ArrayList<Fragment> mFragments;
    private String[] titles = {"四个多啊", "五", "6", "7777"};

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_live, null);
    }

    @Override
    protected void init() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < 2; i++)
            mFragments.add(new TwoFragment());
        super.init();
    }

    @Override
    protected void initViews() {
        mLiveIndexViewpager.setOffscreenPageLimit(2);
        mLiveTab.setViewPager(mLiveIndexViewpager, titles, getActivity(), mFragments);
        mLiveTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mLiveIndexViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    protected void initData() {

    }

}
