package com.github.lxs.peep.ui.dy.ui;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BaseFragment;
import com.github.lxs.peep.bean.dy.HomeCarousel;
import com.github.lxs.peep.http.ApiManager;
import com.github.lxs.peep.http.HttpResponse;
import com.github.lxs.peep.http.HttpUrl;
import com.github.lxs.peep.http.HttpUtils;
import com.github.lxs.peep.http.ParamsMapUtils;
import com.github.lxs.peep.ui.dy.ui.adapter.IndexAdapter;
import com.github.lxs.peep.ui.dy.ui.adapter.MenuAdapter;

import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by cl on 2017/3/27.
 */

public class IndexFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshView)
    XRefreshView mRefreshView;

    private IndexAdapter mAdapter;
    private BGABanner mBGABanner;

    private MenuAdapter mMenuAdapter;
    private RecyclerView mMenuRecyclerView;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_index, null);
    }

    @Override
    protected void initViews() {
        mAdapter = new IndexAdapter(mContext);
        mBGABanner = (BGABanner) mAdapter.setHeaderView(R.layout.bgabanner, mRecyclerView);
        mMenuRecyclerView = (RecyclerView) mAdapter.setHeaderView(R.layout.recyclerview, mRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);


        mBGABanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(mContext)
                        .load(model)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .dontAnimate()
                        .into(itemView);
            }
        });

        mMenuAdapter = new MenuAdapter(mContext);
        mMenuRecyclerView.setAdapter(mMenuAdapter);

        mRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                mHandler.postDelayed(() -> mRefreshView.stopRefresh(), 2000);
            }
        });
    }

    private Handler mHandler = new Handler();

    @Override
    protected void initData() {
        Observable<HttpResponse<List<HomeCarousel>>> carousel = HttpUtils.getInstance(mContext)
                .getRetofitClinet()
                .setBaseUrl(HttpUrl.baseUrl)
                .builder(ApiManager.DyApi.class)
                .getCarousel(ParamsMapUtils.getHomeCarousel());
        carousel.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResponse<List<HomeCarousel>>>() {
            @Override
            public void call(HttpResponse<List<HomeCarousel>> listHttpResponse) {
//                mBGABanner.setData(R.drawable., listHttpResponse.getData(), null);
            }
        });
    }
}
