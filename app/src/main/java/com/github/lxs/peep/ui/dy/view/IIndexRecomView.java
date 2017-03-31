package com.github.lxs.peep.ui.dy.view;

import com.github.lxs.peep.base.BaseView;
import com.github.lxs.peep.bean.dy.index.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.index.HomeHotColumn;
import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;

import java.util.List;

/**
 * Created by cl on 2017/3/28.
 */

public interface IIndexRecomView extends BaseView {

    void setBGAData(List<String> datas);

    void setHotColumns(List<HomeHotColumn> homeHotColumns);

    void setFaceScoreColumns(List<HomeFaceScoreColumn> homeFaceScoreColumns);

    void setOtherAllColumns(List<HomeRecommendHotCate> homeRecommendHotCates);

}
