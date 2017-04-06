package com.github.lxs.peep.ui.girl.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.lxs.peep.R;
import com.github.lxs.peep.bean.girl.GirlInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cl on 2017/4/6.
 */

public class Girladapter extends BaseAdapter {

    private Context mContext;
    private List<GirlInfo> mLists;

    public Girladapter(Context mContext, List<GirlInfo> lists) {
        this.mContext = mContext;
        mLists = lists;
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.girl_list_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else mHolder = (ViewHolder) convertView.getTag();
        Glide.with(mContext)
                .load(mLists.get(position).getImage_url())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //用原图的1/10作为缩略图，如果缩略图先被加载出来则先显示缩略图
                .error(R.mipmap.ic_launcher)
                .thumbnail(0.1f)
                .crossFade(300)
                .into(mHolder.mImg);
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.img)
        ImageView mImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
