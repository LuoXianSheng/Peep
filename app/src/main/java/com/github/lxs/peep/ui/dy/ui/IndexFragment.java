package com.github.lxs.peep.ui.dy.ui;

import android.os.Handler;
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
import com.github.lxs.peep.base.MvpFragment;
import com.github.lxs.peep.bean.dy.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.HomeHotColumn;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
import com.github.lxs.peep.di.component.DaggerDYComponent;
import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.ui.dy.presenter.IndexPresenter;
import com.github.lxs.peep.ui.dy.ui.adapter.IndexAdapter;
import com.github.lxs.peep.ui.dy.ui.adapter.MenuAdapter;
import com.github.lxs.peep.ui.dy.view.IIndexView;
import com.github.lxs.peep.widget.refresh.SmileyHeaderView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by cl on 2017/3/27.
 */

public class IndexFragment extends MvpFragment<IIndexView, IndexPresenter> implements IIndexView, MenuAdapter.MenuItemClick {

    @BindView(R.id.refreshView)
    XRefreshView mRefreshView;
    @BindView(R.id.dy_index_listview)
    ListView mListview;

    private IndexAdapter mAdapter;
    private BGABanner mBGABanner;
    private MenuAdapter mMenuAdapter;

    private Handler mHandler = new Handler();

    @Inject
    IndexPresenter mPresenter;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_index, null);
    }

    @Override
    protected void initViews() {
        initRefresh();
        mListview.addHeaderView(initHeadView());

        mAdapter = new IndexAdapter(mContext);
        mListview.setAdapter(mAdapter);

        mListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL:
                    case SCROLL_STATE_FLING:
                        Glide.with(IndexFragment.this).pauseRequests();
                        break;
                    case SCROLL_STATE_IDLE:
                        Glide.with(IndexFragment.this).resumeRequests();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private View initHeadView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dy_index_head_view, null);
        mBGABanner = ButterKnife.findById(view, R.id.bgAbabner);
        mBGABanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                KLog.e(model);
                Glide.with(IndexFragment.this)
                        .load(model)
                        .placeholder(R.mipmap.dy_image_loading)
                        .error(R.mipmap.dy_image_error)
                        .dontAnimate()
                        .into(itemView);
            }
        });

        RecyclerView menuRecyclerView = ButterKnife.findById(view, R.id.recyclerview);
        menuRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        mMenuAdapter = new MenuAdapter(mContext, this);
        menuRecyclerView.setAdapter(mMenuAdapter);
        return view;
    }

    private void initRefresh() {
        mRefreshView.setCustomHeaderView(new SmileyHeaderView(mContext));
        mRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
//                mHandler.postDelayed(() -> mRefreshView.stopRefresh(), 2000);
                initData();
            }
        });

        mRefreshView.setPinnedTime(1000);
        mRefreshView.setPullLoadEnable(false);
        mRefreshView.setPullRefreshEnable(true);
        mRefreshView.setMoveForHorizontal(true);
        mRefreshView.setPinnedContent(true);
    }

    @Override
    protected IndexPresenter createPresenter() {
        DaggerDYComponent.builder().dYModule(new DYModule(this)).build().inject(this);
        return mPresenter;
    }

    @Override
    protected void initData() {
        showLoading();
        mPresenter.loadBGAData();
        mPresenter.loadMenuData();
        mPresenter.loadHotColumns();
        mPresenter.loadFaceScoreColumns(0, 4);
        mPresenter.loadOtherAllColumns();
    }

    @Override
    public void setBGAData(List<String> datas) {
        mBGABanner.setData(datas, null);
    }

    @Override
    public void setMenuData(List<String> datas) {
        mMenuAdapter.clear();
        mMenuAdapter.addData(datas);
    }

    @Override
    public void setHotColumns(List<HomeHotColumn> homeHotColumns) {
        mAdapter.refreshHotListData(homeHotColumns);
    }

    @Override
    public void setFaceScoreColumns(List<HomeFaceScoreColumn> homeFaceScoreColumns) {
        mAdapter.refreshFaceScoreListData(homeFaceScoreColumns);
    }

    @Override
    public void setOtherAllColumns(List<HomeRecommendHotCate> homeRecommendHotCates) {
        mAdapter.refreshOtherAllListData(homeRecommendHotCates);
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }

    @Override
    public void showLoading() {
        mRefreshView.startRefresh();
    }

    @Override
    public void hideLoading() {
        mRefreshView.stopRefresh();
    }

    @Override
    public void onMenuItemClick(int position) {
        showToast(position + "");
    }

    @Override
    public void onStop() {
        super.onStop();
        Glide.with(mContext).pauseRequests();
    }
}
