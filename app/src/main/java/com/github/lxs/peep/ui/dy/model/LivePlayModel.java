package com.github.lxs.peep.ui.dy.model;

import com.github.lxs.peep.bean.dy.OldLiveVideoInfo;
import com.github.lxs.peep.http.ApiManager;
import com.github.lxs.peep.http.HttpResponse;
import com.github.lxs.peep.http.HttpUrl;
import com.github.lxs.peep.http.HttpUtils;
import com.github.lxs.peep.listener.MySubscriber;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.utils.MD5Util;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cl on 2017/4/1.
 */

public class LivePlayModel {

    public void loadPlayInfo(String roomId, OnLoadCompleteListener<OldLiveVideoInfo> listener) {
        int time = (int) (System.currentTimeMillis() / 1000);
        String str = "lapi/live/thirdPart/getPlay/" + roomId + "?aid=pcclient&rate=0&time=" + time + "9TUk5fjjUjg9qIMH3sdnh";
        String auth = MD5Util.getToMd5Low32(str);

//        L.e("地址为:" + NetWorkApi.baseUrl + NetWorkApi.getLiveVideo + room_id + "?" + tempParams.toString());
        Request requestPost = new Request.Builder()
                .url(HttpUrl.oldBaseUrl + HttpUrl.getOldLiveVideo + roomId + "?rate=0")
                .get()
                .addHeader("aid", "pcclient")
                .addHeader("auth", auth)
                .addHeader("time", time + "")
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                KLog.e(e.getMessage() + "---失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string().toString();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.getInt("error") == 0) {
                        Gson gson = new Gson();
                        OldLiveVideoInfo mLiveVideoInfo = gson.fromJson(json, OldLiveVideoInfo.class);
                        listener.onLoadSuccess(mLiveVideoInfo);
                    } else {
                        listener.onLoadFailed("获取数据失败!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
