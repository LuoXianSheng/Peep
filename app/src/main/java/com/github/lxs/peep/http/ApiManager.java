package com.github.lxs.peep.http;

import com.github.lxs.peep.bean.db.Top250;
import com.github.lxs.peep.bean.dy.OldLiveVideoInfo;
import com.github.lxs.peep.bean.dy.index.HomeCarousel;
import com.github.lxs.peep.bean.dy.index.HomeColumnMoreAllList;
import com.github.lxs.peep.bean.dy.index.HomeColumnMoreOtherList;
import com.github.lxs.peep.bean.dy.index.HomeColumnMoreTwoCate;
import com.github.lxs.peep.bean.dy.index.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.index.HomeHotColumn;
import com.github.lxs.peep.bean.dy.index.HomeRecommendHotCate;
import com.github.lxs.peep.bean.dy.index.HomeCateList;
import com.github.lxs.peep.bean.dy.live.LiveAllList;
import com.github.lxs.peep.bean.dy.live.LiveOtherColumn;
import com.github.lxs.peep.bean.dy.live.LiveOtherList;
import com.github.lxs.peep.bean.dy.live.LiveOtherTwoColumn;
import com.github.lxs.peep.bean.dy.live.LiveSportsAllList;
import com.github.lxs.peep.bean.girl.GirlInfo;
import com.github.lxs.peep.bean.test;
import com.github.lxs.peep.http.response.DyHttpResponse;
import com.github.lxs.peep.http.response.GirlHttpResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

import static com.github.lxs.peep.http.HttpUrl.getCarousel;
import static com.github.lxs.peep.http.HttpUrl.getHomeCate;
import static com.github.lxs.peep.http.HttpUrl.getHomeCateList;
import static com.github.lxs.peep.http.HttpUrl.getHomeColumnMoreAllList;
import static com.github.lxs.peep.http.HttpUrl.getHomeColumnMoreCate;
import static com.github.lxs.peep.http.HttpUrl.getHomeColumnMoreOtherList;
import static com.github.lxs.peep.http.HttpUrl.getHomeFaceScoreColumn;
import static com.github.lxs.peep.http.HttpUrl.getHomeHotColumn;
import static com.github.lxs.peep.http.HttpUrl.getHomeRecommendHotCate;
import static com.github.lxs.peep.http.HttpUrl.getLiveAllList;
import static com.github.lxs.peep.http.HttpUrl.getLiveOtherColumn;
import static com.github.lxs.peep.http.HttpUrl.getLiveOtherTwoColumn;
import static com.github.lxs.peep.http.HttpUrl.getLiveOtherTwoList;
import static com.github.lxs.peep.http.HttpUrl.getLiveSportsAllList;

public interface ApiManager {

    interface DyIndexApi {
        /**
         * 首页分类列表
         *
         * @return
         */
        @GET(getHomeCateList)
        Observable<DyHttpResponse<List<HomeCateList>>> getHomeCateList(@QueryMap Map<String, String> params);

        /**
         * 首页 列表详情页
         *
         * @return
         */
        @GET(getHomeCate)
        Observable<DyHttpResponse<List<HomeRecommendHotCate>>> getHomeCate(@QueryMap Map<String, String> params);

        /**
         * 首页   推荐轮播图
         *
         * @return
         */
        @GET(getCarousel)
        Observable<DyHttpResponse<List<HomeCarousel>>> getCarousel(@QueryMap Map<String, String> params);

        /**
         * 推荐---最热
         *
         * @return
         */
        @GET(getHomeHotColumn)
        Observable<DyHttpResponse<List<HomeHotColumn>>> getHotColumn(@QueryMap Map<String, String> params);

        /**
         * 推荐---颜值
         *
         * @return
         */
        @GET(getHomeFaceScoreColumn)
        Observable<DyHttpResponse<List<HomeFaceScoreColumn>>> getFaceScoreColumn(@QueryMap Map<String, String> params);

        /**
         * 推荐---热门 种类
         *
         * @return
         */
        @GET(getHomeRecommendHotCate)
        Observable<DyHttpResponse<List<HomeRecommendHotCate>>> getHotCate(@QueryMap Map<String, String> params);


        /**
         * 栏目 更多   --二级分类列表
         *
         * @return
         */
        @GET(getHomeColumnMoreCate)
        Observable<DyHttpResponse<List<HomeColumnMoreTwoCate>>> getHomeColumnMoreCate(@QueryMap Map<String, String> params);

        /**
         * 栏目 更多   --其他列表
         *
         * @return
         */
        @GET(getHomeColumnMoreOtherList)
        Observable<DyHttpResponse<List<HomeColumnMoreOtherList>>> getHomeColumnMoreOtherList(@QueryMap Map<String, String> params);

        /**
         * 栏目 更多   --全部列表
         *
         * @return
         */
        @GET(getHomeColumnMoreAllList + "{cate_id}")
        Observable<DyHttpResponse<List<HomeColumnMoreAllList>>> getHomeColumnMoreAllList(@Path("cate_id") String cate_id, @QueryMap Map<String, String> params);

    }

    interface DyLiveApi {
        /**
         * 直播其他栏目分类
         *
         * @return
         */
        @GET(getLiveOtherColumn)
        Observable<DyHttpResponse<List<LiveOtherColumn>>> getLiveOtherColumn(@QueryMap Map<String, String> params);

        /**
         * 全部直播
         *
         * @return
         */
        @GET(getLiveAllList)
        Observable<DyHttpResponse<List<LiveAllList>>> getLiveAllList(@QueryMap Map<String, String> params);

        /**
         * 直播其他栏目二级分类
         *
         * @return
         */
        @GET(getLiveOtherTwoColumn)
        Observable<DyHttpResponse<List<LiveOtherTwoColumn>>> getLiveOtherTwoColumn(@QueryMap Map<String, String> params);

        /**
         * 直播其他列表页
         *
         * @return
         */
        @GET(getLiveOtherTwoList + "{cate_id}")
        Observable<DyHttpResponse<List<LiveOtherList>>> getLiveOtherList(@Path("cate_id") String cate_id, @QueryMap Map<String, String> params);

        /**
         * 体育直播
         *
         * @return
         */
        @GET(getLiveSportsAllList)
        Observable<DyHttpResponse<List<LiveSportsAllList>>> getLiveSportsAllList(@QueryMap Map<String, String> params);


        @GET("lapi/live/thirdPart/getPlay/{roomId}")
        Observable<DyHttpResponse<OldLiveVideoInfo>> getLiveInfo(@Path("roomId") String cate_id, @QueryMap Map<String, String> params);
    }

    interface GirlApi {
        //        http://image.baidu.com/channel/listjson?pn=2&rn=5&tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8&ie=utf8&qq-pf-to=pcqq.c2c
        @GET("channel/listjson?ie=utf8")
        Observable<String> getGirls(@Query("pn") int index, @Query("rn") int limit,
                                  @Query("tag1") String tag1, @Query("tag2") String tag2);
    }

    @GET("top250")
    Observable<Top250> getTop250Movie(@Query("start") int start, @Query("count") int count);
}
