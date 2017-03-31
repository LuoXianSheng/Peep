package com.github.lxs.peep.ui.dy.view;

import com.github.lxs.peep.base.BaseView;
import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;

import java.util.List;

/**
 * Created by cl on 2017/3/30.
 */

public interface IIndexOtherMenuView extends BaseView {

    void setOtherCate(List<HomeRecommendHotCate> homeCates);

}
