package com.github.lxs.peep.ui.dy.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BaseFragment;
import com.github.lxs.peep.ui.dy.ui.adapter.DYFragmentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/24.
 */

public class ViewPagerFragment extends BaseFragment {


    @BindView(R.id.dy_tab)
    MagicIndicator mIndicator;
    @BindView(R.id.dy_viewpager)
    ViewPager mViewpager;

    private ArrayList<Fragment> mFragments;
    private String[] titles = {"第一个", "二", "三个", "四个多啊"};
    private DYFragmentAdapter mAdapter;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_tab_viewpager, null);
    }

    @Override
    protected void init() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++)
            mFragments.add(new OtherFragment());
        super.init();
    }

    @Override
    protected void initViews() {
        mViewpager.setOffscreenPageLimit(2);
        mAdapter = new DYFragmentAdapter(getChildFragmentManager(), mFragments);
        mViewpager.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

}
