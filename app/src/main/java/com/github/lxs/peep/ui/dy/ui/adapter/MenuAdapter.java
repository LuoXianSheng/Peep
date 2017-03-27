package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lxs.peep.R;
import com.github.lxs.peep.baseadapter.BaseAdapter;
import com.github.lxs.peep.baseadapter.BaseViewHolder;
import com.github.lxs.peep.bean.dy.IndexCateList;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/27.
 */

public class MenuAdapter extends BaseAdapter<IndexCateList> {

    private Context mContext;

    public MenuAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public MenuAdapter(Context context, boolean useAnimation) {
        super(context, useAnimation);
        mContext = context;
    }

    @Override
    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        return new CateListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.circle_text_item, parent, false));
    }

    class CateListViewHolder extends BaseViewHolder {

        @BindView(R.id.img)
        ImageView mImg;
        @BindView(R.id.text)
        TextView mText;

        public CateListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(int position) {
            IndexCateList item = mData.get(position);
            mText.setText(item.getTitle());
        }
    }
}
