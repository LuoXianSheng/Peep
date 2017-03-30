package com.github.lxs.peep.ui.dy.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BaseFragment;
import com.github.lxs.peep.ui.dy.ui.adapter.DYFragmentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/24.
 */

public class DYFragment extends BaseFragment {

    @BindView(R.id.dy_tab)
    MagicIndicator mIndicator;
    @BindView(R.id.dy_viewpager)
    ViewPager mIndexViewpager;

    private DYFragmentAdapter mAdapter;
    private ArrayList<Fragment> mFragments;
    private String[] titles = {"首页", "全部直播", "第一个", "二", "四个多啊"};

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_tab_viewpager, null);
    }

    @Override
    protected void init() {
        mFragments = new ArrayList<>();
        mFragments.add(new IndexFragment());
        mFragments.add(new ViewPagerFragment());
        mFragments.add(new OtherFragment());
        mFragments.add(new OtherFragment());
        mFragments.add(new OtherFragment());
        super.init();
    }

    @Override
    protected void initViews() {
        mIndexViewpager.setOffscreenPageLimit(2);
        mAdapter = new DYFragmentAdapter(getChildFragmentManager(), mFragments);
        mIndexViewpager.setAdapter(mAdapter);
        initIndicator();
    }

    private void initIndicator() {
        mIndicator.setBackgroundColor(Color.parseColor("#FF7700"));
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setScrollPivotX(0.15f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#AAffffff"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setTextSize(15);
                simplePagerTitleView.setOnClickListener(v -> mIndexViewpager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                TriangularPagerIndicator indicator = new TriangularPagerIndicator(context);
                indicator.setLineColor(Color.WHITE);
                indicator.setLineHeight(0);
                indicator.setTriangleHeight(UIUtil.dip2px(mContext, 5D));
                return indicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mIndexViewpager);
    }

    @Override
    protected void initData() {

    }
}
