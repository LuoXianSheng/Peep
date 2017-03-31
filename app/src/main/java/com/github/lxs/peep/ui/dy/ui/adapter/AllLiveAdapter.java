package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.baseadapter.BaseViewHolder;
import com.github.lxs.peep.bean.dy.live.LiveAllList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cl on 2017/3/31.
 */

public class AllLiveAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<LiveAllList> mLists;

    public AllLiveAdapter(Context context, List<LiveAllList> mLists) {
        this.mContext = context;
        this.mLists = mLists;
    }

    @Override
    public BaseViewHolder getViewHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.dy_room_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, boolean isItem) {
        if (holder instanceof ViewHolder) {
            ViewHolder mHolder = (ViewHolder) holder;
            LiveAllList item = mLists.get(position);
            Glide.with(mContext)
                    .load(item.getRoom_src())
                    .crossFade()
                    .placeholder(R.mipmap.dy_img_loading)
                    .error(R.mipmap.dy_img_loading_error)
                    .into(mHolder.mRoomImg);
            mHolder.mTvOnlineNum.setText(item.getOnline() + "");
            mHolder.mTvName.setText(item.getNickname());
            mHolder.mTvRoomName.setText(item.getRoom_name());
        }
    }


    @Override
    public int getAdapterItemCount() {
        return mLists.size();
    }

    public void refreshData(List<LiveAllList> mLists) {
        this.mLists.clear();
        this.mLists.addAll(mLists);
    }

    public void loadMroe(List<LiveAllList> mLists) {
        this.mLists.addAll(mLists);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.room_img)
        ImageView mRoomImg;
        @BindView(R.id.tv_online_num)
        TextView mTvOnlineNum;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_room_name)
        TextView mTvRoomName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
