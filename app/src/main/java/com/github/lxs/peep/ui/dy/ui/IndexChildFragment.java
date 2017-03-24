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

public class IndexChildFragment extends BaseFragment {


    @BindView(R.id.dy_live_index_child_viewpager)
    ViewPager mViewpager;
    @BindView(R.id.dy_live_index_child_tab)
    SlidingTabLayout mTab;

    private int idx;

    private ArrayList<Fragment> mFragments;
    private String[] titles = {"第一个", "二", "三个", "四个多啊"};
    private DYFragmentAdapter mAdapter;


    public IndexChildFragment() {
    }

    public IndexChildFragment(int idx) {
        this.idx = idx;
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_index_child, null);
    }

    @Override
    protected void init() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++)
            mFragments.add(new IndexChildTwoFragment());
        super.init();
    }

    @Override
    protected void initViews() {
//        mViewpager.setOffscreenPageLimit(2);
//        mTab.setViewPager(mViewpager, titles, getActivity(), mFragments);
//        mTab.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                mViewpager.setCurrentItem(position);
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//
//            }
//        });
        mViewpager.setOffscreenPageLimit(2);
        mAdapter = new DYFragmentAdapter(getChildFragmentManager(), mFragments);
        mViewpager.setAdapter(mAdapter);
        mTab.setViewPager(mViewpager, titles);
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewpager.setCurrentItem(position);
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
