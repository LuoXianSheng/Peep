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
import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;
import com.github.lxs.peep.image.GlideTransform;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cl on 2017/3/28.
 */

public class IndexOtherAdapter extends BaseAdapter {

    private List<HomeRecommendHotCate> homeCates;

    private Context mContext;

    public IndexOtherAdapter(Context context, List<HomeRecommendHotCate> homeCates) {
        mContext = context;
        this.homeCates = homeCates;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dy_cate_gridview, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
            AutoUtils.autoSize(convertView);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        HomeRecommendHotCate item = homeCates.get(position);
        PubItemAdapter pubItemAdapter = new PubItemAdapter(item.getRoom_list(), mContext);
        mHolder.mRvColumnList.setAdapter(pubItemAdapter);
        if (position == 0) {
            mHolder.mTitle.setText("最热");
            mHolder.mItemIcon.setImageResource(R.mipmap.icon_hot);
        } else {
            mHolder.mTitle.setText(item.getTag_name());
            Glide.with(mContext)
                    .load(item.getIcon_url())
                    .dontAnimate()
                    .transform(new GlideTransform(mContext, GlideTransform.CIRCLE))
                    .into(mHolder.mItemIcon);
        }
        return convertView;
    }

    public void refreshListData(List<HomeRecommendHotCate> homeCates) {
        this.homeCates.clear();
        this.homeCates.addAll(homeCates);
        notifyDataSetChanged();
    }


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
