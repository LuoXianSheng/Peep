package com.github.lxs.peep.http;

import com.github.lxs.peep.bean.db.Top250;
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
        Observable<HttpResponse<List<HomeCateList>>> getHomeCateList(@QueryMap Map<String, String> params);

        /**
         * 首页 列表详情页
         *
         * @return
         */
        @GET(getHomeCate)
        Observable<HttpResponse<List<HomeRecommendHotCate>>> getHomeCate(@QueryMap Map<String, String> params);

        /**
         * 首页   推荐轮播图
         *
         * @return
         */
        @GET(getCarousel)
        Observable<HttpResponse<List<HomeCarousel>>> getCarousel(@QueryMap Map<String, String> params);

        /**
         * 推荐---最热
         *
         * @return
         */
        @GET(getHomeHotColumn)
        Observable<HttpResponse<List<HomeHotColumn>>> getHotColumn(@QueryMap Map<String, String> params);

        /**
         * 推荐---颜值
         *
         * @return
         */
        @GET(getHomeFaceScoreColumn)
        Observable<HttpResponse<List<HomeFaceScoreColumn>>> getFaceScoreColumn(@QueryMap Map<String, String> params);

        /**
         * 推荐---热门 种类
         *
         * @return
         */
        @GET(getHomeRecommendHotCate)
        Observable<HttpResponse<List<HomeRecommendHotCate>>> getHotCate(@QueryMap Map<String, String> params);


        /**
         * 栏目 更多   --二级分类列表
         *
         * @return
         */
        @GET(getHomeColumnMoreCate)
        Observable<HttpResponse<List<HomeColumnMoreTwoCate>>> getHomeColumnMoreCate(@QueryMap Map<String, String> params);

        /**
         * 栏目 更多   --其他列表
         *
         * @return
         */
        @GET(getHomeColumnMoreOtherList)
        Observable<HttpResponse<List<HomeColumnMoreOtherList>>> getHomeColumnMoreOtherList(@QueryMap Map<String, String> params);

        /**
         * 栏目 更多   --全部列表
         *
         * @return
         */
        @GET(getHomeColumnMoreAllList + "{cate_id}")
        Observable<HttpResponse<List<HomeColumnMoreAllList>>> getHomeColumnMoreAllList(@Path("cate_id") String cate_id, @QueryMap Map<String, String> params);

    }

    interface DyLiveApi {
        /**
         * 直播其他栏目分类
         *
         * @return
         */
        @GET(getLiveOtherColumn)
        Observable<HttpResponse<List<LiveOtherColumn>>> getLiveOtherColumn(@QueryMap Map<String, String> params);

        /**
         * 全部直播
         *
         * @return
         */
        @GET(getLiveAllList)
        Observable<HttpResponse<List<LiveAllList>>> getLiveAllList(@QueryMap Map<String, String> params);

        /**
         * 直播其他栏目二级分类
         *
         * @return
         */
        @GET(getLiveOtherTwoColumn)
        Observable<HttpResponse<List<LiveOtherTwoColumn>>> getLiveOtherTwoColumn(@QueryMap Map<String, String> params);

        /**
         * 直播其他列表页
         *
         * @return
         */
        @GET(getLiveOtherTwoList + "{cate_id}")
        Observable<HttpResponse<List<LiveOtherList>>> getLiveOtherList(@Path("cate_id") String cate_id, @QueryMap Map<String, String> params);

        /**
         * 体育直播
         *
         * @return
         */
        @GET(getLiveSportsAllList)
        Observable<HttpResponse<List<LiveSportsAllList>>> getLiveSportsAllList(@QueryMap Map<String, String> params);

    }

    @GET("top250")
    Observable<Top250> getTop250Movie(@Query("start") int start, @Query("count") int count);
}
