package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lxs.peep.R;
import com.github.lxs.peep.bean.dy.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.HomeHotColumn;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cl on 2017/3/28.
 */

public class IndexAdapter extends BaseAdapter {

    public static final int TYPE_1 = 1;//最热
    public static final int TYPE_2 = 2;//颜值
    public static final int TYPE_3 = 3;//其它所有

    private static final int MAX_TYPE = 3;

    private HotAdapter mHotAdapter;
    private List<HomeHotColumn> mHotColumns;

    private FaceScoreAdapter mFaceScoreAdapter;
    private List<HomeFaceScoreColumn> mFaceScoreColumns;

    private OtherAllAdapter mOtherAllAdapter;
    private List<HomeRecommendHotCate> mOtherAllColumns;

    private Hot mHot;

    private Context mContext;

    public IndexAdapter(Context context) {
        mContext = context;


        mHotColumns = new ArrayList<>();

        mFaceScoreAdapter = new FaceScoreAdapter(mContext);
        mFaceScoreColumns = new ArrayList<>();

        mOtherAllAdapter = new OtherAllAdapter(mContext);
        mOtherAllColumns = new ArrayList<>();
    }

    private int size;

    @Override
    public int getCount() {
        return size + 2;//加上hot，Face
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else if (position == 1) {
            return TYPE_2;
        } else {
            return TYPE_3;
        }
    }

    @Override
    public int getViewTypeCount() {
        return MAX_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        KLog.e(viewType + "-----viewType");
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dy_cate_item, parent, false);
            switch (viewType) {
                case TYPE_1:
                    break;
                case TYPE_2:
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.dy_cate_item, parent, false);
                    break;
                case TYPE_3:
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.dy_cate_item, parent, false);
                    break;
            }
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        switch (viewType) {
            case TYPE_1:
//                mHotAdapter = new HotAdapter(mContext);
//                mHolder.mTitle.setText("最热");
//                mHolder.mRvColumnList.setLayoutManager(new GridLayoutManager(mContext, 2));
//                mHolder.mRvColumnList.setAdapter(mHotAdapter);
//                String[] data = {"a", "a", "a", "a", "a", "a"};
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, data);
                mHot = new Hot(new ArrayList<>(), mContext);
                mHolder.mRvColumnList.setAdapter(mHot);
                break;
            case TYPE_2:
//                mFaceScoreAdapter = new FaceScoreAdapter(mContext);
//                mHolder.mTitle.setText("第二热");
//                mHolder.mRvColumnList.setLayoutManager(new GridLayoutManager(mContext, 2));
//                mHolder.mRvColumnList.setAdapter(mFaceScoreAdapter);
                break;
            case TYPE_3:
//                mOtherAllAdapter = new OtherAllAdapter(mContext);
//                mHolder.mTitle.setText("不热");
//                mHolder.mRvColumnList.setLayoutManager(new GridLayoutManager(mContext, 2));
//                mHolder.mRvColumnList.setAdapter(mOtherAllAdapter);
                break;
        }
        return convertView;
    }

    public void refreshHotListData(List<HomeHotColumn> mHotColumns) {
//        KLog.e(mHotColumns.size() + "-------------mHotColumns");
//        mHotAdapter.addData(mHotColumns);
//        mHotAdapter.notifyDataSetChanged();
        size = mHotColumns.size();
//        notifyDataSetChanged();
        mHot.refreshListData(mHotColumns);
        notifyDataSetChanged();
    }

//    public void refreshFaceScoreListData(List<HomeFaceScoreColumn> mFaceScoreColumns) {
//        KLog.e(mFaceScoreColumns.size() + "-------------mFaceScoreColumns");
//        mFaceScoreAdapter.addData(mFaceScoreColumns);
//        mFaceScoreAdapter.notifyDataSetChanged();
//    }
//
//    public void refreshOtherAllListData(List<HomeRecommendHotCate> mOtherAllColumns) {
//        KLog.e(mOtherAllColumns.size() + "-------------mOtherAllColumns");
//        mOtherAllAdapter.addData(mOtherAllColumns);
//        mOtherAllAdapter.notifyDataSetChanged();
//        this.mOtherAllColumns = mOtherAllColumns;
//        notifyDataSetChanged();
//    }


    class ViewHolder {
        @BindView(R.id.item_icon)
        ImageView mItemIcon;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.gridView)
        GridView mRvColumnList;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
