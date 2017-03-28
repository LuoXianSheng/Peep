package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.baseadapter.BaseAdapter;
import com.github.lxs.peep.baseadapter.BaseViewHolder;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
import com.socks.library.KLog;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/27.
 */

public class OtherAllAdapter extends BaseAdapter<HomeRecommendHotCate> {
    public OtherAllAdapter(Context context) {
        super(context);
    }

    public OtherAllAdapter(Context context, boolean useAnimation) {
        super(context, useAnimation);
    }

    @Override
    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        return new OtherAllAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.dy_room_item, parent, false));
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.room_img)
        ImageView mRoomImg;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(int position) {
            KLog.e(position + "--------------------------" + getData().get(position).getIcon_url());
            Glide.with(mContext)
                    .load(getData().get(position).getIcon_url())
                    .crossFade()
                    .placeholder(R.mipmap.dy_image_loading)
                    .error(R.mipmap.dy_image_error)
                    .into(mRoomImg);
        }
    }
}
