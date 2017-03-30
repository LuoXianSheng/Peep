package com.github.lxs.peep.ui.dy.view;

import com.github.lxs.peep.base.BaseView;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;

import java.util.List;

/**
 * Created by cl on 2017/3/30.
 */

public interface IOtherMenuView extends BaseView {

    void setOtherCate(List<HomeRecommendHotCate> homeCates);

}
