package com.github.lxs.peep.ui.dy.view;

import com.github.lxs.peep.base.BaseView;
import com.github.lxs.peep.bean.dy.index.HomeCateList;
import com.github.lxs.peep.bean.dy.live.LiveOtherColumn;

import java.util.List;

/**
 * Created by cl on 2017/3/30.
 */

public interface ITabMenuView<T> extends BaseView {

    void setTabMenuBaseData(List<T> menuBaseData);

}
