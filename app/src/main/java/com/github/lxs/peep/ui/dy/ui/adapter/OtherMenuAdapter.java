package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.bean.dy.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.HomeHotColumn;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
import com.github.lxs.peep.image.GlideTransform;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cl on 2017/3/28.
 */

public class OtherMenuAdapter extends BaseAdapter {

    private static final int TYPE_1 = 0;//最热
    private static final int TYPE_2 = 1;//其它所有

    private static final int MAX_TYPE = 2;

    private List<HomeHotColumn> mHotColumns;

    private List<HomeRecommendHotCate> homeCates;

    private HotAdapter mHotAdapter;

    private Context mContext;

    public OtherMenuAdapter(Context context, List<HomeRecommendHotCate> homeCates) {
        mContext = context;
        this.homeCates = homeCates;
//        mHotColumns = new ArrayList<>();
//        mHotAdapter = new HotAdapter(mHotColumns, mContext);
//
//        mOtherAllColumns = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return homeCates.size();//加上hot
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
//        if (position == 0) {
//            return TYPE_1;
//        } else {
            return TYPE_2;
//        }
    }

    @Override
    public int getViewTypeCount() {
        return MAX_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dy_cate_gridview, parent, false);
//            switch (viewType) {
//                case TYPE_1:
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.dy_cate_gridview, parent, false);
//                    break;
//                case TYPE_2:
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.dy_cate_gridview, parent, false);
//                    break;
//                case TYPE_3:
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.dy_cate_gridview, parent, false);
//                    break;
//            }
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
            AutoUtils.autoSize(convertView);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        switch (viewType) {
            case TYPE_1:
                mHolder.mTitle.setText("最热");
                mHolder.mRvColumnList.setAdapter(mHotAdapter);
                mHolder.mItemIcon.setImageResource(R.mipmap.icon_hot);
                break;
            case TYPE_2:
                HomeRecommendHotCate item = homeCates.get(position);//因为有hot，所以position相对于集合来说会大1
                AllOtherAdapter allOtherAdapter = new AllOtherAdapter(item.getRoom_list(), mContext);
                mHolder.mRvColumnList.setAdapter(allOtherAdapter);
                mHolder.mTitle.setText(item.getTag_name());
                Glide.with(mContext)
                        .load(item.getIcon_url())
                        .dontAnimate()
                        .transform(new GlideTransform(mContext, GlideTransform.CIRCLE))
                        .into(mHolder.mItemIcon);
                break;
        }
        return convertView;
    }

    public void refreshHotListData(List<HomeHotColumn> mHotColumns) {
        this.mHotColumns.clear();
        this.mHotColumns.addAll(mHotColumns);
        mHotAdapter.refreshListData(mHotColumns);
    }

//    public void refreshOtherAllListData(List<HomeRecommendHotCate> mOtherAllColumns) {
//        this.mOtherAllColumns.clear();
//        this.mOtherAllColumns.addAll(mOtherAllColumns);
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
