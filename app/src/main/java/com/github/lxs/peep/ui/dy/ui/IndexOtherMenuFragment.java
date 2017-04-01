package com.github.lxs.peep.ui.dy.ui;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.base.MvpFragment;
import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;
import com.github.lxs.peep.di.component.DaggerDYFragmentComponent;
import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.ui.dy.presenter.IndexOtherMenuPresenter;
import com.github.lxs.peep.ui.dy.ui.adapter.IndexOtherAdapter;
import com.github.lxs.peep.ui.dy.ui.adapter.IndexOtherMenuRecycAdapter;
import com.github.lxs.peep.ui.dy.ui.adapter.IndexOtherMenuPagerAdapter;
import com.github.lxs.peep.ui.dy.view.IIndexOtherMenuView;
import com.github.lxs.peep.widget.refresh.SmileyHeaderView;
import com.socks.library.KLog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cl on 2017/3/30.
 */

public class IndexOtherMenuFragment extends MvpFragment<IIndexOtherMenuView, IndexOtherMenuPresenter> implements IIndexOtherMenuView {

    @BindView(R.id.dy_listview)
    ListView mDyListview;
    @BindView(R.id.refreshView)
    XRefreshView mRefreshView;


    private IndexOtherAdapter mAdapter;
    private String type;

    @Inject
    IndexOtherMenuPresenter mPresenter;
    private IndexOtherMenuPagerAdapter menuPagerAdapter;
    private View pagerView;
    private ViewPager mViewPager;

    public IndexOtherMenuFragment() {
    }

    public IndexOtherMenuFragment(String type, int delayedTime) {
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

    private void initRefresh() {
        mRefreshView.setCustomHeaderView(new SmileyHeaderView(mContext));
        mRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                mHandler.postDelayed(() -> initData(), 500);
            }
        });

        mRefreshView.setPinnedTime(1000);
        mRefreshView.setPullLoadEnable(false);
        mRefreshView.setPullRefreshEnable(true);
        mRefreshView.setMoveForHorizontal(true);
        mRefreshView.setPinnedContent(true);

        pagerView = LayoutInflater.from(mContext).inflate(R.layout.dy_other_menu_vierpager, null);
        mViewPager = ButterKnife.findById(pagerView, R.id.dy_viewpager);
        menuPagerAdapter = new IndexOtherMenuPagerAdapter(new ArrayList<>());
        mViewPager.setAdapter(menuPagerAdapter);

        mDyListview.addHeaderView(pagerView);

        mAdapter = new IndexOtherAdapter(mContext, new ArrayList<>());
        mDyListview.setAdapter(mAdapter);

        mDyListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL:
                    case SCROLL_STATE_FLING:
                        Glide.with(IndexOtherMenuFragment.this).pauseRequests();
                        break;
                    case SCROLL_STATE_IDLE:
                        Glide.with(IndexOtherMenuFragment.this).resumeRequests();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void setOtherCate(List<HomeRecommendHotCate> homeCates) {
        refreshHeadView(homeCates);
        mAdapter.refreshListData(homeCates);
        mRefreshView.stopRefresh();
    }

    private void refreshHeadView(List<HomeRecommendHotCate> homeCates) {
        List<View> mViews = new ArrayList<>();
        int mPageSize = (int) Math.ceil(homeCates.size() / 8);
        homeCates.remove(0);//去除最热
        for (int i = 0; i < mPageSize; i++) {
            if (i <= 1) {//只分两页
                RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(mContext).inflate(R.layout.include_recyclerview, null);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                IndexOtherMenuRecycAdapter recycAdapter = new IndexOtherMenuRecycAdapter(mContext);
                recyclerView.setAdapter(recycAdapter);
                List<HomeRecommendHotCate> tempCates = new ArrayList<>();
                int size = homeCates.size() >= (i + 1) * 8 ? 8 : (homeCates.size() - i * 8);
                for (int j = i * 8; j < size + i * 8; j++) {
                    tempCates.add(homeCates.get(j));
                }
                recycAdapter.addData(tempCates);
                mViews.add(recyclerView);
            }
        }
        MagicIndicator mIndicator = ButterKnife.findById(pagerView, R.id.dy_tab);
        CircleNavigator circleNavigator = new CircleNavigator(mContext);
        circleNavigator.setCircleCount(mViews.size());
        circleNavigator.setCircleColor(Color.parseColor("#FF7700"));
        circleNavigator.setCircleClickListener(mViewPager::setCurrentItem);
        mIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(mIndicator, mViewPager);
        menuPagerAdapter.refreshView(mViews);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        hideLoadView();
    }

    @Override
    public void showError(String error) {
        hideLoadView();
        showLoadErrorView();
        KLog.e(error);
    }

    @Override
    protected IndexOtherMenuPresenter createPresenter() {
        DaggerDYFragmentComponent.builder().dYModule(new DYModule(this)).build().inject(this);
        return mPresenter;
    }
}
