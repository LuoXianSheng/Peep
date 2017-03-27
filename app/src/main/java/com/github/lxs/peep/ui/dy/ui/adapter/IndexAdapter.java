package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.github.lxs.peep.R;
import com.github.lxs.peep.baseadapter.BaseViewHolder;
import com.github.lxs.peep.bean.dy.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.HomeHotColumn;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
import com.github.lxs.peep.ui.dy.ui.adapter.FaceScoreAdapter;
import com.github.lxs.peep.ui.dy.ui.adapter.HotAdapter;
import com.github.lxs.peep.ui.dy.ui.adapter.OtherAllAdapter;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/27.
 */

public class IndexAdapter extends BaseRecyclerAdapter {

    public static final int TYPE_1 = 1;//最热
    public static final int TYPE_2 = 2;//颜值
    public static final int TYPE_3 = 3;//其它所有

    private HotAdapter mHotAdapter;
    private List<HomeHotColumn> mHotColumns;

    private FaceScoreAdapter mFaceScoreAdapter;
    private List<HomeFaceScoreColumn> mFaceScoreColumns;

    private OtherAllAdapter mOtherAllAdapter;
    private List<HomeRecommendHotCate> mRecommendHotCates;

    private Context mContext;

    public IndexAdapter(Context context) {
        mContext = context;

        mHotAdapter = new HotAdapter(mContext);
        mHotColumns = new ArrayList<>();

        mFaceScoreAdapter = new FaceScoreAdapter(mContext);
        mFaceScoreColumns = new ArrayList<>();

        mOtherAllAdapter = new OtherAllAdapter(mContext);
        mRecommendHotCates = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new A(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        KLog.e(viewType);
        return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.dy_cate_item, parent, false));
//        switch (viewType) {
//            case TYPE_1:
//                return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.dy_cate_item, parent, false));
//            case TYPE_2:
//                return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.dy_cate_item, parent, false));
//            case TYPE_3:
//                return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.dy_cate_item, parent, false));
//            default:
//                return null;
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, boolean isItem) {
        KLog.e(position);
        if (position == TYPE_1) {

        } else if (position == TYPE_2) {

        } else {

        }
    }

    @Override
    public int getAdapterItemViewType(int position) {
        switch (position) {
            case 1:
                return TYPE_1;
            case 2:
                return TYPE_2;
            default:
                return TYPE_3;
        }
    }

    @Override
    public int getAdapterItemCount() {
        return mRecommendHotCates.size();
    }

    class ContentViewHolder extends BaseViewHolder {
        @BindView(R.id.item_icon)
        ImageView mItemIcon;
        @BindView(R.id.rv_column_list)
        RecyclerView mRvColumnList;

        public ContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(int position) {
            HomeHotColumn item = mHotAdapter.getData().get(position);
            mRvColumnList.setLayoutManager(new GridLayoutManager(mContext, 2));
            mRvColumnList.setAdapter(mHotAdapter);
        }
    }

    class A extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon)
        ImageView mItemIcon;
        @BindView(R.id.rv_column_list)
        RecyclerView mRvColumnList;

        public A(View itemView) {
            super(itemView);
        }
    }

}
