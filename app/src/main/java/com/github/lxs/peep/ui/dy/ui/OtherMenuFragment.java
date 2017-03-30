package com.github.lxs.peep.ui.dy.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.base.MvpFragment;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
import com.github.lxs.peep.bean.dy.IndexCateList;
import com.github.lxs.peep.di.component.DaggerDYComponent;
import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.ui.dy.presenter.OtherMenuPresenter;
import com.github.lxs.peep.ui.dy.ui.adapter.OtherMenuAdapter;
import com.github.lxs.peep.ui.dy.ui.adapter.OtherMenuPagerAdapter;
import com.github.lxs.peep.ui.dy.ui.adapter.OtherMenuRecycAdapter;
import com.github.lxs.peep.ui.dy.ui.adapter.RecommendAdapter;
import com.github.lxs.peep.ui.dy.view.IOtherMenuView;
import com.github.lxs.peep.widget.refresh.SmileyHeaderView;
import com.socks.library.KLog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by cl on 2017/3/30.
 */

public class OtherMenuFragment extends MvpFragment<IOtherMenuView, OtherMenuPresenter> implements IOtherMenuView {

    @BindView(R.id.dy_listview)
    ListView mDyListview;
    @BindView(R.id.refreshView)
    XRefreshView mRefreshView;


    private OtherMenuAdapter mAdapter;
    private String type;

    public OtherMenuFragment() {
    }

    public OtherMenuFragment(String type, int delayedTime) {
        super.setDelayedTime(delayedTime);
        this.type = type;
    }


    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_refresh_listview, null);
    }

    @Override
    protected void initViews() {
        initRefresh();
    }

    @Override
    protected void initData() {
        mPresenter.loadOtherMenuData(type);
    }


    private View initHeadView(List<HomeRecommendHotCate> homeCates) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dy_other_menu_vierpager, null);
        ButterKnife.findById(view, R.id.dy_tab).setVisibility(View.GONE);
        ViewPager mViewPager = ButterKnife.findById(view, R.id.dy_viewpager);
        List<View> mViews = new ArrayList<>();
        int mPageSize = (int) Math.ceil(homeCates.size() / 8);
        for (int i = 0; i < mPageSize; i++) {
            RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(mContext).inflate(R.layout.recyclerview, null);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
            recyclerView.setAdapter(new OtherMenuRecycAdapter(mContext, homeCates));
            mViews.add(recyclerView);
        }
        OtherMenuPagerAdapter menuPagerAdapter = new OtherMenuPagerAdapter(mViews);
        mViewPager.setAdapter(menuPagerAdapter);

        MagicIndicator mIndicator = ButterKnife.findById(view, R.id.dy_tab);
        CircleNavigator circleNavigator = new CircleNavigator(mContext);
        circleNavigator.setCircleCount(mViews.size());
        circleNavigator.setCircleColor(Color.parseColor("#FF7700"));
        circleNavigator.setCircleClickListener(mViewPager::setCurrentItem);
        mIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(mIndicator, mViewPager);
        return view;
    }

    private void initRefresh() {
        mRefreshView.setCustomHeaderView(new SmileyHeaderView(mContext));
        mRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }
        });

        mRefreshView.setPinnedTime(1000);
        mRefreshView.setPullLoadEnable(true);
        mRefreshView.setPullRefreshEnable(true);
//        mRefreshView.setMoveForHorizontal(true);
        mRefreshView.setPinnedContent(true);
    }

    @Override
    public void setOtherCate(List<HomeRecommendHotCate> homeCates) {
        mDyListview.addHeaderView(initHeadView(homeCates));

        mAdapter = new OtherMenuAdapter(mContext, homeCates);
        mDyListview.setAdapter(mAdapter);

        mDyListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL:
                    case SCROLL_STATE_FLING:
                        Glide.with(OtherMenuFragment.this).pauseRequests();
                        break;
                    case SCROLL_STATE_IDLE:
                        Glide.with(OtherMenuFragment.this).resumeRequests();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        removeLoadStatusView();
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected OtherMenuPresenter createPresenter() {
        DaggerDYComponent.builder().dYModule(new DYModule(this)).build().inject(this);
        return mPresenter;
    }
}
