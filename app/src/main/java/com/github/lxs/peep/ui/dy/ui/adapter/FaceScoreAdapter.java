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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.lxs.peep.R;
import com.github.lxs.peep.bean.dy.index.HomeFaceScoreColumn;
import com.github.lxs.peep.ui.dy.ui.PcLivePlayActivity;
import com.github.lxs.peep.ui.dy.ui.PhoneLivePlayActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by cl on 2017/3/28.
 */

public class FaceScoreAdapter extends BaseAdapter {

    private List<HomeFaceScoreColumn> mList;
    private Context mContext;

    public FaceScoreAdapter(List<HomeFaceScoreColumn> list, Context context) {
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
        HomeFaceScoreColumn item = mList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dy_room_facescore_item, parent, false);
            mHolder = new ViewHolder(convertView);
            mHolder.mCardView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, PcLivePlayActivity.class);
                if (item.getCate_id().equals("201"))
                    intent.setClass(mContext, PhoneLivePlayActivity.class);
                else
                    intent.setClass(mContext, PcLivePlayActivity.class);
                intent.putExtra("roomName", mList.get(position).getRoom_name());
                intent.putExtra("roomId", mList.get(position).getRoom_id());
                mContext.startActivity(intent);
            });
            convertView.setTag(mHolder);
            AutoUtils.autoSize(convertView);
        } else mHolder = (ViewHolder) convertView.getTag();
        Glide.with(mContext)
                .load(item.getVertical_src())
                .crossFade(300)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.mipmap.dy_img_loading_big)
                .error(R.mipmap.dy_img_loading_error_big)
                .into(mHolder.mRoomImg);
        if (item.getCate_id().equals("201")) mHolder.mMobleLive.setVisibility(View.VISIBLE);
        else mHolder.mMobleLive.setVisibility(View.GONE);
        mHolder.mTvOnlineNum.setText(item.getOnline() + "");
        mHolder.mTvName.setText(item.getNickname());
        mHolder.mTvLocate.setText(item.getAnchor_city());
        return convertView;
    }

    public void refreshListData(List<HomeFaceScoreColumn> mHotColumns) {
        mList.clear();
        mList.addAll(mHotColumns);
        notifyDataSetChanged();
    }

    class ViewHolder {
        @BindView(R.id.cardView)
        CardView mCardView;
        @BindView(R.id.mobile_live)
        ImageView mMobleLive;
        @BindView(R.id.room_img)
        ImageView mRoomImg;
        @BindView(R.id.tv_online_num)
        TextView mTvOnlineNum;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_locate)
        TextView mTvLocate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
