package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;
import com.github.lxs.peep.bean.dy.live.LiveAllList;
import com.github.lxs.peep.ui.dy.ui.PcLivePlayActivity;
import com.github.lxs.peep.ui.dy.ui.PhoneLivePlayActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by cl on 2017/3/28.
 */

public class PubItemAdapter<T> extends BaseAdapter {

    private List<T> mList;
    private Context mContext;

    public PubItemAdapter(List<T> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            mHolder.mCardView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, PcLivePlayActivity.class);
                String roomName = "";
                String roomId = "";
                if (mList.get(position) instanceof HomeRecommendHotCate.RoomListEntity) {
                    HomeRecommendHotCate.RoomListEntity item = (HomeRecommendHotCate.RoomListEntity) mList.get(position);
                    roomName = item.getRoom_name();
                    roomId = item.getRoom_id();
                    if (item.getCate_id().equals("201"))
                        intent.setClass(mContext, PhoneLivePlayActivity.class);
                    else
                        intent.setClass(mContext, PcLivePlayActivity.class);
                } else if (mList.get(position) instanceof LiveAllList) {
                    LiveAllList item = (LiveAllList) mList.get(position);
                    roomName = item.getRoom_name();
                    roomId = item.getRoom_id();
                    if (item.getCate_id() == 201)
                        intent.setClass(mContext, PhoneLivePlayActivity.class);
                    else
                        intent.setClass(mContext, PcLivePlayActivity.class);
                }
                intent.putExtra("roomName", roomName);
                intent.putExtra("roomId", roomId);
                mContext.startActivity(intent);
            });
            convertView.setTag(mHolder);
        } else mHolder = (ViewHolder) convertView.getTag();
        String imgUrl = "";
        String onLineNum = "0";
        String nickName = "";
        String roomName = "";
        if (mList.get(position) instanceof HomeRecommendHotCate.RoomListEntity) {
            HomeRecommendHotCate.RoomListEntity item = (HomeRecommendHotCate.RoomListEntity) mList.get(position);
            imgUrl = item.getRoom_src();
            onLineNum = item.getOnline() + "";
            nickName = item.getNickname();
            roomName = item.getRoom_name();
        } else if (mList.get(position) instanceof LiveAllList) {
            LiveAllList item = (LiveAllList) mList.get(position);
            imgUrl = item.getRoom_src();
            onLineNum = item.getOnline() + "";
            nickName = item.getNickname();
            roomName = item.getRoom_name();
        }
        Glide.with(mContext)
                .load(imgUrl)
                .crossFade()
                .placeholder(R.mipmap.dy_img_loading)
                .error(R.mipmap.dy_img_loading_error)
                .into(mHolder.mRoomImg);
        mHolder.mTvOnlineNum.setText(onLineNum);
        mHolder.mTvName.setText(nickName);
        mHolder.mTvRoomName.setText(roomName);
        return convertView;
    }

    public void refreshData(List<T> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void loadMore(List<T> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder {
        @BindView(R.id.cardView)
        CardView mCardView;
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
