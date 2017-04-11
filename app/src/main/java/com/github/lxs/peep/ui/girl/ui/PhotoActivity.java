package com.github.lxs.peep.ui.girl.ui;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BaseActivity;
import com.github.lxs.peep.bean.girl.GirlInfo;
import com.github.lxs.peep.listener.MySubscriber;
import com.github.lxs.peep.utils.DownloadUtil;
import com.github.lxs.peep.utils.PermissionUtil;
import com.github.lxs.peep.utils.StatusBarUtil;
import com.socks.library.KLog;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by cl on 2017/4/10.
 */

public class PhotoActivity extends BaseActivity {

    @BindView(R.id.dy_viewpager)
    ViewPager mViewpager;
    @BindView(R.id.top_name)
    TextView mName;
    @BindView(R.id.layout)
    AutoLinearLayout mLayout;

    private List<GirlInfo> mLists;
    private Animation animTopIn, animTopOut;

    private boolean isShowTopLayout = false;
    private Handler mHandler = new Handler();
    private int currPos;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initViews() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        StatusBarUtil.setTransparent(this);

        animTopIn = AnimationUtils.loadAnimation(mContext, R.anim.pop_top_in);
        animTopOut = AnimationUtils.loadAnimation(mContext, R.anim.pop_top_out);

        currPos = getIntent().getIntExtra("pos", 0);
        mLists = getIntent().getParcelableArrayListExtra("mLists");
        mName.setText(mLists.get(currPos).getAbs());

        PhotoAdapter adapter = new PhotoAdapter(mContext, mLists);
        mViewpager.setAdapter(adapter);
        mViewpager.setCurrentItem(currPos);

        mViewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currPos = position;
                mName.setText(mLists.get(position).getAbs());
            }
        });

        mHandler.postDelayed(this::showTopLayout, 500);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.top_back)
    public void btnBack() {
        finish();
    }

    @OnClick(R.id.top_download)
    public void btnDownLoad() {
        PermissionUtil.requestPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                Observable
                        .create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                try {
                                    Bitmap b = Glide.with(mContext)
                                            .load(mLists.get(currPos).getDownload_url())
                                            .asBitmap()
                                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                            .get();
                                    subscriber.onNext(DownloadUtil.saveImage(b));
                                    subscriber.onCompleted();
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            PhotoActivity.super.showToast(s);
                        });
            }

            @Override
            public void onRequestPermissionRefuse() {
                showToast("您已经拒绝了SD卡的写入权限，需要您授权才能使用此功能！");
            }

            @Override
            public void onRequestPermissionFailed() {
                showToast("拒绝授权，此功能将不可以使用！");
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
    }

    private void showTopLayout() {
        isShowTopLayout = true;
        mLayout.setVisibility(View.VISIBLE);
        mLayout.startAnimation(animTopIn);

        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void hideTopLayout() {
        isShowTopLayout = false;
        mLayout.setVisibility(View.GONE);
        mLayout.startAnimation(animTopOut);

        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void callBack() {
        if (isShowTopLayout) hideTopLayout();
        else showTopLayout();
    }

    class PhotoAdapter extends PagerAdapter {

        private Context mContext;
        private List<GirlInfo> mLists;

        PhotoAdapter(Context context, List<GirlInfo> lists) {
            mContext = context;
            mLists = lists;
        }

        @Override
        public int getCount() {
            return mLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout layout = new LinearLayout(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            layout.setLayoutParams(params);
            layout.setGravity(Gravity.CENTER);
            layout.setBackgroundColor(Color.BLACK);
            PhotoView photoView = new PhotoView(mContext);
            photoView.setLayoutParams(params);
            photoView.setOnViewTapListener((view, x, y) -> callBack());
            layout.addView(photoView);
            container.addView(layout);
            Glide.with(mContext)
                    .load(mLists.get(position).getImage_url())
                    .into(photoView);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
