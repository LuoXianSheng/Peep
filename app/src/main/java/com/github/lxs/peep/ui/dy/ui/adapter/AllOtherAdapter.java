package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by cl on 2017/3/28.
 */

public class AllOtherAdapter extends BaseAdapter {

    private List<HomeRecommendHotCate.RoomListEntity> mList;
    private Context mContext;

    public AllOtherAdapter(List<HomeRecommendHotCate.RoomListEntity> list, Context context) {
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
        HomeRecommendHotCate.RoomListEntity item = mList.get(position);
        Glide.with(mContext)
                .load(item.getRoom_src())
                .crossFade()
                .placeholder(R.mipmap.dy_image_loading)
                .error(R.mipmap.dy_image_error)
                .into(mHolder.mRoomImg);
        mHolder.mTvOnlineNum.setText(item.getOnline() + "");
        mHolder.mTvName.setText(item.getNickname());
        mHolder.mTvRoomName.setText(item.getRoom_name());
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.room_img)
        ImageView mRoomImg;
        @BindView(R.id.tv_online_num)
        TextView mTvOnlineNum;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_room_name)
        TextView mTvRoomName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
