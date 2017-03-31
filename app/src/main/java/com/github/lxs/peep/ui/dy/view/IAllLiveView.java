package com.github.lxs.peep.ui.dy.view;

import com.github.lxs.peep.base.BaseView;
import com.github.lxs.peep.bean.dy.live.LiveAllList;

import java.util.List;

/**
 * Created by cl on 2017/3/31.
 */

public interface IAllLiveView extends BaseView {

    void setLiveData(List<LiveAllList> mLists);

    void loadMore(List<LiveAllList> mLists);
}
