package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.bean.dy.HomeHotColumn;
import com.socks.library.KLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by cl on 2017/3/28.
 */

public class Hot extends BaseAdapter {

    private List<HomeHotColumn> mList;
    private Context mContext;

    public Hot(List<HomeHotColumn> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dy_room_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else mHolder = (ViewHolder) convertView.getTag();
        KLog.e(position + "--------------------------" + mList.get(position).getRoom_src());
        Glide.with(mContext)
                .load(mList.get(position).getRoom_src())
                .crossFade()
                .placeholder(R.mipmap.dy_image_loading)
                .error(R.mipmap.dy_image_error)
                .into(mHolder.mRoomImg);
        return convertView;
    }

    public void refreshListData(List<HomeHotColumn> mHotColumns) {
        mList.clear();
        for (int i = 0; i < mHotColumns.size(); i++) {
            mList.add(mHotColumns.get(i));
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.room_img)
        ImageView mRoomImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
