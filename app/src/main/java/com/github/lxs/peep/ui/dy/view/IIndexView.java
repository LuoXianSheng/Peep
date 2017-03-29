package com.github.lxs.peep.ui.dy.view;

import com.github.lxs.peep.base.BaseView;
import com.github.lxs.peep.bean.dy.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.HomeHotColumn;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;

import java.util.List;

/**
 * Created by cl on 2017/3/28.
 */

public interface IIndexView extends BaseView {

    void setBGAData(List<String> datas);

    void setMenuData(List<String> datas);

    void setHotColumns(List<HomeHotColumn> homeHotColumns);

    void setFaceScoreColumns(List<HomeFaceScoreColumn> homeFaceScoreColumns);

    void setOtherAllColumns(List<HomeRecommendHotCate> homeRecommendHotCates);

    void showError(String error);
}
