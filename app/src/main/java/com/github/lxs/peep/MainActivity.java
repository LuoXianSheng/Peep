package com.github.lxs.peep;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.lxs.peep.base.BaseActivity;
import com.github.lxs.peep.baseadapter.ListViewDataAdapter;
import com.github.lxs.peep.baseadapter.ViewHolderBase;
import com.github.lxs.peep.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.lodemore_list_view)
    LoadMoreListView mLoadMoreListView;

    private List<Integer> mDatas;
    private ListViewDataAdapter mAdapter;
    private Handler mHandler = new Handler();
    private boolean isTopRefresh = true;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add(i);
        }
        super.init();
    }

    @Override
    protected void initViews() {
        mAdapter = new ListViewDataAdapter<Integer>(position -> new ViewHolderBase<Integer>() {

            private TextView tv;

            @Override
            public View createView(LayoutInflater layoutInflater) {
                View contentView = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
                tv = ButterKnife.findById(contentView, android.R.id.text1);
                return contentView;
            }

            @Override
            public void showData(int position, Integer itemData) {
                tv.setText(mAdapter.getDataList().get(position) + "-" + itemData);
            }
        }, new ArrayList<>());
        mLoadMoreListView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(() -> mHandler.postDelayed(() -> {
            isTopRefresh = true;
            initData();
        }, 2000));
        mLoadMoreListView.setOnLoadMoreListener(() -> mHandler.postDelayed(() -> {
            isTopRefresh = false;
            initData();
        }, 2000));

    }

    @Override
    protected void initData() {
        if (isTopRefresh) {
            mSwipeRefreshLayout.setRefreshing(false);
            mAdapter.refreshList(mDatas);
        } else {
            mLoadMoreListView.onLoadMoreComplete();
            mAdapter.getDataList().addAll(mDatas);
            mAdapter.notifyDataSetChanged();
        }
    }
}
