package com.github.lxs.peep.http;

import com.github.lxs.peep.bean.db.Top250;
import com.github.lxs.peep.bean.dy.HomeCarousel;
import com.github.lxs.peep.bean.dy.HomeColumnMoreAllList;
import com.github.lxs.peep.bean.dy.HomeColumnMoreOtherList;
import com.github.lxs.peep.bean.dy.HomeColumnMoreTwoCate;
import com.github.lxs.peep.bean.dy.HomeFaceScoreColumn;
import com.github.lxs.peep.bean.dy.HomeHotColumn;
import com.github.lxs.peep.bean.dy.HomeRecommendHotCate;
import com.github.lxs.peep.bean.dy.IndexCateList;

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

public interface ApiManager {

    interface DyApi {
        /**
         * 首页分类列表
         *
         * @return
         */
        @GET(getHomeCateList)
        Observable<HttpResponse<List<IndexCateList>>> getHomeCateList(@QueryMap Map<String, String> params);

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

    @GET("top250")
    Observable<Top250> getTop250Movie(@Query("start") int start, @Query("count") int count);
}
