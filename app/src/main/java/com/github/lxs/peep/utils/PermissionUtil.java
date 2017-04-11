package com.github.lxs.peep.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.github.lxs.peep.listener.MySubscriber;
import com.socks.library.KLog;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PermissionUtil {

    public interface RequestPermission {
        void onRequestPermissionSuccess();

        void onRequestPermissionRefuse();//之前的请求过被拒绝

        void onRequestPermissionFailed();//第一次请求，拒绝

    }

    public static void requestPermission(Activity mActivity, String permission, RequestPermission requestPermission) {
        if (ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                //之前请求过权限被拒绝，返回true；请求过勾选了不在提示，或者第三方安全软件拒绝，为true
                requestPermission.onRequestPermissionRefuse();
                return;
            }
            RxPermissions rxPermissions = new RxPermissions(mActivity);
            rxPermissions.request(permission)
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MySubscriber<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            if (aBoolean) {
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                                requestPermission.onRequestPermissionFailed();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            KLog.e(e.getMessage());
                            requestPermission.onRequestPermissionSuccess();
                        }
                    });
        } else {
            requestPermission.onRequestPermissionSuccess();
        }
    }
}
