package com.github.lxs.peep.bean.dy;

import java.util.List;

/**
 * Created by cl on 2017/3/28.
 */

public class Dy {

    private List<HomeHotColumn> mHotColumns;
    private List<HomeFaceScoreColumn> mFaceScoreColumns;
    private List<HomeRecommendHotCate> mOtherAllColumns;


    public List<HomeHotColumn> getHotColumns() {
        return mHotColumns;
    }

    public void setHotColumns(List<HomeHotColumn> hotColumns) {
        mHotColumns = hotColumns;
    }

    public List<HomeFaceScoreColumn> getFaceScoreColumns() {
        return mFaceScoreColumns;
    }

    public void setFaceScoreColumns(List<HomeFaceScoreColumn> faceScoreColumns) {
        mFaceScoreColumns = faceScoreColumns;
    }

    public List<HomeRecommendHotCate> getOtherAllColumns() {
        return mOtherAllColumns;
    }

    public void setOtherAllColumns(List<HomeRecommendHotCate> otherAllColumns) {
        mOtherAllColumns = otherAllColumns;
    }
}
