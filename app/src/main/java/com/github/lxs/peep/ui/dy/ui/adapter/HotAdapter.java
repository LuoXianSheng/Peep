package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.github.lxs.peep.baseadapter.BaseAdapter;
import com.github.lxs.peep.baseadapter.BaseViewHolder;
import com.github.lxs.peep.bean.dy.HomeHotColumn;

/**
 * Created by cl on 2017/3/27.
 */

public class HotAdapter extends BaseAdapter<HomeHotColumn> {
    public HotAdapter(Context context) {
        super(context);
    }

    public HotAdapter(Context context, boolean useAnimation) {
        super(context, useAnimation);
    }

    @Override
    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
