package com.github.lxs.peep.ui.girl.view;

import com.github.lxs.peep.base.BaseView;
import com.github.lxs.peep.bean.girl.GirlInfo;

import java.util.List;

/**
 * Created by cl on 2017/4/6.
 */

public interface IGirlView extends BaseView{

    void setDatas(List<GirlInfo> mDatas);

    void loadMore(List<GirlInfo> mDatas);
}
