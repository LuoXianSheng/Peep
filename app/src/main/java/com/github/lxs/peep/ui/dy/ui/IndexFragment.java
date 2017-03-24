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

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/24.
 */

public class IndexFragment extends BaseFragment {

    @BindView(R.id.dy_index_tab)
    SlidingTabLayout mIndexTab;
    @BindView(R.id.dy_index_viewpager)
    ViewPager mIndexViewpager;

    private DYFragmentAdapter mAdapter;
    private ArrayList<Fragment> mFragments;
    private String[] titles = {"第一个", "二", "四个多啊"};

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_index, null);
    }

    @Override
    protected void init() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++)
            mFragments.add(new IndexChildFragment(i));
        super.init();
    }

    @Override
    protected void initViews() {
        mIndexViewpager.setOffscreenPageLimit(2);
        mAdapter = new DYFragmentAdapter(getChildFragmentManager(), mFragments);
        mIndexViewpager.setAdapter(mAdapter);
        mIndexTab.setViewPager(mIndexViewpager, titles);
        mIndexTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mIndexViewpager.setCurrentItem(position);
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
