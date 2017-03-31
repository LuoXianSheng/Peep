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
import com.github.lxs.peep.base.MvpFragment;
import com.github.lxs.peep.bean.dy.live.LiveOtherColumn;
import com.github.lxs.peep.di.component.DaggerDYComponent;
import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.ui.dy.presenter.TabMenuPresenter;
import com.github.lxs.peep.ui.dy.ui.adapter.DYFragmentAdapter;
import com.github.lxs.peep.ui.dy.view.ITabMenuView;

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
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/24.
 */

public class DYFragment extends MvpFragment<ITabMenuView, TabMenuPresenter> implements ITabMenuView<LiveOtherColumn> {

    @BindView(R.id.dy_tab)
    MagicIndicator mIndicator;
    @BindView(R.id.dy_viewpager)
    ViewPager mIndexViewpager;

    @Inject
    TabMenuPresenter mPresenter;

    private DYFragmentAdapter mAdapter;
    private ArrayList<Fragment> mFragments;
    private String[] titles;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_tab_viewpager, null);
    }

    @Override
    protected TabMenuPresenter createPresenter() {
        DaggerDYComponent.builder().dYModule(new DYModule(this)).build().inject(this);
        return mPresenter;
    }


    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        mPresenter.loadTabMenuBase();
    }

    @Override
    public void setTabMenuBaseData(List<LiveOtherColumn> menuBaseData) {
        mFragments = new ArrayList<>();
        titles = new String[menuBaseData.size() + 2];
        titles[0] = "首页";
        mFragments.add(new IndexFragment());
        titles[1] = "全部直播";
        mFragments.add(new AllLiveFragment());
        for (int i = 0; i < menuBaseData.size(); i++) {
            titles[i + 2] = menuBaseData.get(i).getCate_name();
            mFragments.add(new AllLiveFragment());
        }

        mIndexViewpager.setOffscreenPageLimit(5);
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String error) {

    }
}
