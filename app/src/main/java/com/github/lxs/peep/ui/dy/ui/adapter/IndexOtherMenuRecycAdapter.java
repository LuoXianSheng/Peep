package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.baseadapter.BaseAdapter;
import com.github.lxs.peep.baseadapter.BaseViewHolder;
import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;
import com.github.lxs.peep.image.GlideTransform;
import com.socks.library.KLog;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/30.
 */

public class IndexOtherMenuRecycAdapter extends BaseAdapter<HomeRecommendHotCate> {


    public IndexOtherMenuRecycAdapter(Context context) {
        super(context);
    }


    @Override
    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.circle_text_item, null));
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.img)
        ImageView mImg;
        @BindView(R.id.text)
        TextView mText;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(int position) {
            HomeRecommendHotCate item = mData.get(position);
            mText.setText(item.getTag_name());
            Glide.with(mContext)
                    .load(item.getIcon_url())
                    .transform(new GlideTransform(mContext, GlideTransform.CIRCLE))
                    .crossFade()
                    .into(mImg);
        }
    }
}
