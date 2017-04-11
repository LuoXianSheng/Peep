package com.github.lxs.peep.ui.main.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.lxs.peep.R;
import com.github.lxs.peep.utils.PermissionUtil;

/**
 * Created by cl on 2017/4/10.
 */

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_splash);

        mHandler.postDelayed(() -> PermissionUtil.requestPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess() {
                        mHandler.postDelayed(() -> {
                            Toast.makeText(SplashActivity.this, "拥有权限", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }, 2000);
                    }

                    @Override
                    public void onRequestPermissionRefuse() {
                        mHandler.post(() -> Toast.makeText(SplashActivity.this, "之前已经拒绝过授权了", Toast.LENGTH_SHORT).show());
                        mHandler.postDelayed((SplashActivity.this::finish), 1000);
                    }

                    @Override
                    public void onRequestPermissionFailed() {
                        mHandler.post(() -> Toast.makeText(SplashActivity.this, "拒绝授权，关闭", Toast.LENGTH_SHORT).show());
                        mHandler.postDelayed(SplashActivity.this::finish, 1000);
                    }
                }), 1000);
    }
}