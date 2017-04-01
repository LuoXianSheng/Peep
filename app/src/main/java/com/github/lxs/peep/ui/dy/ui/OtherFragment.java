package com.github.lxs.peep.ui.dy.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BaseFragment;
import com.github.lxs.peep.bean.dy.OldLiveVideoInfo;
import com.github.lxs.peep.http.HttpUrl;
import com.github.lxs.peep.listener.MySubscriber;
import com.github.lxs.peep.utils.MD5Util;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by cl on 2017/3/24.
 */

public class OtherFragment extends BaseFragment {


    @BindView(R.id.listview)
    ListView mListview;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_dy_other, null);
    }

    @Override
    protected void initViews() {
        String[] data = {"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        KLog.e("初始化数据");
        int room_id = 522423;
        int time = (int) (System.currentTimeMillis() / 1000);
        String str = "lapi/live/thirdPart/getPlay/" + room_id + "?aid=pcclient&rate=0&time=" + time + "9TUk5fjjUjg9qIMH3sdnh";
        String auth = MD5Util.getToMd5Low32(str);
//        L.e("地址为:"+NetWorkApi.baseUrl + NetWorkApi.getLiveVideo + room_id+"?"+tempParams.toString());
        Request requestPost = new Request.Builder()
                .url(HttpUrl.oldBaseUrl + HttpUrl.getOldLiveVideo + room_id + "?rate=0")
                .get()
                .addHeader("aid", "pcclient")
                .addHeader("auth", auth)
                .addHeader("time", time + "")
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                KLog.e(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String result  = null;
                        try {
                            result = response.body().string().toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }
                }).map(s -> new Gson().fromJson(s, OldLiveVideoInfo.class))
                        .subscribe(new MySubscriber<OldLiveVideoInfo>() {
                            @Override
                            public void onSuccess(OldLiveVideoInfo oldLiveVideoInfo) {
                                KLog.e("url-----" + oldLiveVideoInfo.getData());
                            }

                            @Override
                            public void onError(Throwable e) {
                                KLog.e(e.getMessage());
                            }
                        });
            }
        });
    }
}
