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

/**
 * Created by cl on 2017/4/1.
 */

public class LivePlayModel {

    public void loadPlayInfo(String roomId, OnLoadCompleteListener<OldLiveVideoInfo> listener) {
        int time = (int) (System.currentTimeMillis() / 1000);
        String str = "lapi/live/thirdPart/getPlay/" + roomId + "?aid=pcclient&rate=0&time=" + time + "9TUk5fjjUjg9qIMH3sdnh";
        String auth = MD5Util.getToMd5Low32(str);

        Map<String, String> map = new HashMap<>();
        map.put("aid", "pcclient");
        map.put("auth", auth);
        map.put("time", time + "");

        HttpUtils.getInstance()
                .getRetofitClinet()
                .setBaseUrl(HttpUrl.oldBaseUrl)
                .builder(ApiManager.DyLiveApi.class)
                .getLiveInfo(roomId, map)
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(HttpResponse::getData)
                .subscribe(new MySubscriber<OldLiveVideoInfo>() {
                    @Override
                    public void onSuccess(OldLiveVideoInfo oldLiveVideoInfo) {
                        listener.onLoadSuccess(oldLiveVideoInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                    }
                });
    }
}


//        L.e("地址为:"+NetWorkApi.baseUrl + NetWorkApi.getLiveVideo + room_id+"?"+tempParams.toString());
//        Request requestPost=new Request.Builder()
//        .url(HttpUrl.oldBaseUrl+HttpUrl.getOldLiveVideo+roomId+"?rate=0")
//        .get()
//        .addHeader("aid","pcclient")
//        .addHeader("auth",auth)
//        .addHeader("time",time+"")
//        .build();
//        OkHttpClient client=new OkHttpClient();
//        client.newCall(requestPost).enqueue(new Callback(){
//@Override
//public void onFailure(Call call,IOException e){
//        KLog.e(e.getMessage());
//        }
//
//@Override
//public void onResponse(Call call,Response response)throws IOException{
////                String result = response.body().string().toString();
////                try {
////                    JSONObject object = new JSONObject(result);
////                    if (object.getInt("error") == 0) {
////                        listener.onLoadSuccess(new Gson().fromJson(result, OldLiveVideoInfo.class));
////                    }
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//        Observable.create(new Observable.OnSubscribe<String>(){
//@Override
//public void call(Subscriber<?super String>subscriber){
//        String result=null;
//        try{
//        result=response.body().string().toString();
//        }catch(IOException e){
//        e.printStackTrace();
//        }
//        subscriber.onNext(result);
//        subscriber.onCompleted();
//        }
//        }).subscribeOn(AndroidSchedulers.mainThread())
//        .map(s->new Gson().fromJson(s,OldLiveVideoInfo.class))
//        .subscribe(new MySubscriber<OldLiveVideoInfo>(){
//@Override
//public void onSuccess(OldLiveVideoInfo oldLiveVideoInfo){
//        KLog.e("url-----"+oldLiveVideoInfo.getData());
//        listener.onLoadSuccess(oldLiveVideoInfo);
//        }
//
//@Override
//public void onError(Throwable e){
//        KLog.e(e.getMessage());
//        }
//        });
//        }
//        });
//        }
//        }
