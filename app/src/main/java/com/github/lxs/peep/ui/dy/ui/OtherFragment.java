package com.github.lxs.peep.ui.dy.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/24.
 */

public class OtherFragment extends BaseFragment {


    @BindView(R.id.listview)
    ListView mListview;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_other, null);
    }

    @Override
    protected void initViews() {
        String[] data = {"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

}
