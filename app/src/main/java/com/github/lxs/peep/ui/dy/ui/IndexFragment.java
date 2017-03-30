package com.github.lxs.peep.ui.dy.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lxs.peep.R;
import com.github.lxs.peep.base.MvpFragment;
import com.github.lxs.peep.bean.dy.IndexCateList;
import com.github.lxs.peep.di.component.DaggerDYComponent;
import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.ui.dy.presenter.IndexPresenter;
import com.github.lxs.peep.ui.dy.ui.adapter.DYFragmentAdapter;
import com.github.lxs.peep.ui.dy.view.IIndexView;
import com.socks.library.KLog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/30.
 */

public class IndexFragment extends MvpFragment<IIndexView, IndexPresenter> implements IIndexView {

    @BindView(R.id.dy_tab)
    MagicIndicator mIndicator;
    @BindView(R.id.dy_viewpager)
    ViewPager mIndexViewpager;

    @Inject
    IndexPresenter mPresenter;

    private DYFragmentAdapter mAdapter;
    private ArrayList<Fragment> mFragments;
    private String[] titles;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_index, null);
    }

    @Override
    protected IndexPresenter createPresenter() {
        DaggerDYComponent.builder().dYModule(new DYModule(this)).build().inject(this);
        return mPresenter;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        mPresenter.loadMenuBaseData();
    }

    @Override
    public void setMenuBaseData(List<IndexCateList> indexCateLists) {
        mFragments = new ArrayList<>();
        titles = new String[indexCateLists.size() + 1];
        mFragments.add(new RecommendFragment());
        titles[0] = "推荐";
        for (int i = 0; i < indexCateLists.size(); i++) {
            KLog.e(indexCateLists.get(i).getShow_order());
            mFragments.add(new OtherMenuFragment(indexCateLists.get(i).getShow_order(), 1500));
            titles[i + 1] = indexCateLists.get(i).getTitle();
        }

        mIndexViewpager.setOffscreenPageLimit(titles.length);
        mAdapter = new DYFragmentAdapter(getChildFragmentManager(), mFragments);
        mIndexViewpager.setAdapter(mAdapter);

        initIndicator();
    }

    private void initIndicator() {
        mIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#8A000000"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FF7700"));
                simplePagerTitleView.setTextSize(12);
                simplePagerTitleView.setOnClickListener(v -> mIndexViewpager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#ebe4e3"));
                indicator.setVerticalPadding(UIUtil.dip2px(context, 5.0D));
                return indicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mIndexViewpager);
    }

    @Override
    public void showLoadView() {

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
