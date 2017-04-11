package com.github.lxs.peep.ui.dy.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lxs.peep.R;
import com.github.lxs.peep.base.MvpActivity;
import com.github.lxs.peep.bean.dy.OldLiveVideoInfo;
import com.github.lxs.peep.danmu.utils.DanmuProcess;
import com.github.lxs.peep.di.component.DaggerDYActivityComponent;
import com.github.lxs.peep.di.module.DYModule;
import com.github.lxs.peep.ui.dy.presenter.LivePlayPresenter;
import com.github.lxs.peep.ui.dy.view.ILivePlayView;
import com.github.lxs.peep.utils.DialogUtils;
import com.github.lxs.peep.utils.NetworkUtils;
import com.github.lxs.peep.utils.StatusBarUtil;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;
import com.socks.library.KLog;
import com.zhy.autolayout.AutoRelativeLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import master.flame.danmaku.controller.IDanmakuView;

public class PhoneLivePlayActivity extends MvpActivity<ILivePlayView, LivePlayPresenter> implements ILivePlayView {

    private static final String TAG = "Peep";

    /**
     * 控制器常量
     */
    private static final int HIDE_CONTROLLER = 100;
    private static final int HIDE_OPERATION = 101;
    private static final int ANIMO_DELAYED = 103;
    private static final int HIDE_STATUS = 104;
    private static final int SHOW_STATUS = 105;
    //延时时间
    private static final int CONTROLLER_HIDE_DELAYED = 5000;
    private static final int STATUS_SHOW_DELAYED = 180;
    private static final int STATUS_HIDE_DELAYED = 4950;
    private static final int CONTROLLER_ANIMO_TIME = 200;// 控制栏动画时间
    private static final int OPERATION_SHOW_DELAYED = 3000;// 亮度、音量时间

    private static final int[] DISPLAYASPECTRATIO = {PLVideoView.ASPECT_RATIO_FIT_PARENT,
            PLVideoView.ASPECT_RATIO_PAVED_PARENT, PLVideoView.ASPECT_RATIO_16_9};

    @BindView(R.id.top_name)
    TextView tvRoomNo;
    @BindView(R.id.VideoView)
    PLVideoView mVideoView;
    @BindView(R.id.fragmeLayout)
    FrameLayout videoLayout;
    @BindView(R.id.img_play)
    ImageView btnPlay;
    @BindView(R.id.operation_volume_brightness)
    View mOperationLayout;// 提示窗口
    @BindView(R.id.operation_bg)
    ImageView mOperationBg;// 提示图片
    @BindView(R.id.operation_tv)
    TextView mOperationTv;// 提示文字
    @BindView(R.id.loadingView)
    View loadingView;
    @BindView(R.id.tv_buffspeed)
    TextView tvSpeed;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.danmaku)
    IDanmakuView mDanmakuView;
    @BindView(R.id.topLayout)
    AutoRelativeLayout mTopLayout;
    @BindView(R.id.bottomLayout)
    AutoRelativeLayout mBottomLayout;

    private AudioManager mAudioManager;

    // 最大声音
    private int mMaxVolume;
    // 当前声音
    private int mVolume = -1;
    // 当前亮度
    private float mBrightness = -1f;

    private Toast mToast = null;
    private String mVideoPath = null;
    private boolean mIsActivityPaused = true;
    private boolean isGestureDetector = false;// 是否已经开始手势操作了
    private boolean isShowOperation = false;// 亮度、音量图标是否显示
    private boolean isShowController = false;
    private long lastTipsTime = 0;// 提示的最后时间
    private boolean isFlowPermission = false;// 播放流量许可（手机网络）
    private boolean isWifi;
    private boolean isMoble;
    private boolean isFirstInitVideo = true;

    private int mDisplayAspectRatio = 0; // 画面模式

    private AlertDialog mPermissionDialog, mLodingDialog;

    private Animation topShowAnimo, bottomShowAnimo, topHideAnimo, bottomHideAnimo;

    private GestureDetector mGestureDetector;

    private boolean isReconnect = false;// 是否正在重连中
    private boolean isStopReconnet = false;// 是否停止重连

    private String liveUrl;
    private String roomId;
    private DanmuProcess mDanmuProcess;

    private BroadcastReceiver mReceiver;

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_ID_RECONNECTING:
                    if (isReconnect) {
                        return;
                    }
                    isReconnect = true;
                    mLodingDialog.dismiss();
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    showToast("正在尝试重新连接...");
                    checkNet();
                    if (isWifi) {
                        mVideoView.setVideoPath(mVideoPath);
                        mVideoView.start();
                        btnPlay.setImageResource(R.mipmap.icon_live_pause);
                        isReconnect = false;
                    } else {
                        if (isMoble) {
                            if (isFlowPermission) {
                                mVideoView.setVideoPath(mVideoPath);
                                mVideoView.start();
                                btnPlay.setImageResource(R.mipmap.icon_live_pause);
                                isReconnect = false;
                            } else {
                                mPermissionDialog.show();
                            }
                        } else {
                            mHandler.sendEmptyMessageDelayed(MESSAGE_ID_RECONNECTING, 8000);
                            btnPlay.setImageResource(R.mipmap.icon_live_play);
                            isReconnect = false;
                        }
                    }
                    break;
                case HIDE_CONTROLLER:
                    hideControllerView();
                    break;
                case HIDE_OPERATION:
                    hideOperationView();
                    break;
                case ANIMO_DELAYED:
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 0.6f;
                    getWindow().setAttributes(lp);
                    break;
                case SHOW_STATUS:
                    showStatus();
                    break;
                case HIDE_STATUS:
                    hideStatus();
                    break;
                default:
                    break;
            }
        }
    };

    private void initPlayer(String liveUrl) {

        KLog.e(liveUrl);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // mVideoView.setCoverView(null);
        // mVideoView.setBufferingIndicator(null);

        // mVideoPath = Environment.getExternalStorageDirectory() + "/2.mp4";
        // mVideoPath = Environment.getExternalStorageDirectory() + "/aa.mov";
//        mVideoPath = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        // mVideoPath = "http://oss.hzlm.com.cn/mp4/D201307005.mp4";

        mVideoPath = liveUrl;
        setOptions(AVOptions.MEDIA_CODEC_AUTO);

        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        // Set some listeners
        mVideoView.setOnInfoListener(mOnInfoListener);
        // mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
//        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);// 直播流无效
//        mVideoView.setOnCompletionListener(mOnCompletionListener);
        // mVideoView.setOnSeekCompleteListener(mOnSeekCompleteListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
//        mVideoView.setOnPreparedListener(mOnPreparedListener);
        mVideoView.setVideoPath(mVideoPath);

        // You can also use a custom `MediaController` widget
        // mMediaController = new MediaController(LiveDetailActivity.this);
        // mVideoView.setMediaController(mMediaController);

        mGestureDetector = new GestureDetector(this, new MyGestureListener());
        videoLayout.setOnTouchListener((v, event) -> {
            if (mGestureDetector.onTouchEvent(event)) {
                return true;
            }
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    endGesture();
                    break;
            }
            return false;
        });

        isFirstInitVideo = false;
        mLodingDialog.dismiss();
        showControllerView();

//        loadingView.setVisibility(View.VISIBLE);
//        btnPlay.setImageResource(R.mipmap.icon_live_pause);
//        mVideoView.start();

        checkNet();
        if (isWifi) {
            loadingView.setVisibility(View.VISIBLE);
            btnPlay.setImageResource(R.mipmap.icon_live_pause);
            mVideoView.start();
        } else {
            if (isMoble) {
                if (isFlowPermission) {
                    loadingView.setVisibility(View.VISIBLE);
                    btnPlay.setImageResource(R.mipmap.icon_live_pause);
                    mVideoView.start();
                } else {
                    mVideoView.stopPlayback();
                    mPermissionDialog.show();
                }
            } else {
                Toast.makeText(mActivity, "请检查您的网络！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 所有按钮点击事件---------开始
     */
    @OnClick(R.id.img_play)
    public void playClick() {
        checkNet();
        if (mVideoView == null) {
            return;
        }
        if (isWifi) {
            videoPlay();
        } else {
            if (isMoble) {
                if (isFlowPermission) {
                    videoPlay();
                } else {
                    mPermissionDialog.show();
                }
            } else {
                Toast.makeText(mActivity, "请检查您的网络！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void videoPlay() {
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
            if (mDanmakuView != null)
                mDanmakuView.pause();
            btnPlay.setImageResource(R.mipmap.icon_live_play);
        } else {
            mVideoView.start();
            btnPlay.setImageResource(R.mipmap.icon_live_pause);
            loadingView.setVisibility(View.GONE);

            if (mDanmakuView != null && mDanmuProcess != null) {
                mDanmakuView.start();
                mDanmuProcess.start();
            }
        }
    }

    /**
     * 所有按钮点击事件---------结束
     */


    /**
     * 发送消息到handle
     *
     * @param type
     * @param delayed
     */
    private void sendEmptyMessageDelayed(int type, int delayed) {
        mHandler.sendEmptyMessageDelayed(type, delayed);
    }

    private void removeControllerMsg() {
        mHandler.removeMessages(HIDE_CONTROLLER);
        mHandler.removeMessages(SHOW_STATUS);
        mHandler.removeMessages(HIDE_STATUS);
    }

    /**
     * 显示控制器
     */
    private void showControllerView() {
        removeControllerMsg();
        isShowController = true;

        mTopLayout.setVisibility(View.VISIBLE);
        mTopLayout.startAnimation(topShowAnimo);

        mBottomLayout.setVisibility(View.VISIBLE);
        mBottomLayout.startAnimation(bottomShowAnimo);

        sendEmptyMessageDelayed(HIDE_CONTROLLER, CONTROLLER_HIDE_DELAYED);
        sendEmptyMessageDelayed(SHOW_STATUS, STATUS_SHOW_DELAYED);
        sendEmptyMessageDelayed(HIDE_STATUS, STATUS_HIDE_DELAYED);
    }

    /**
     * 隐藏控制器
     */
    private void hideControllerView() {
        isShowController = false;
        removeControllerMsg();

        mTopLayout.setVisibility(View.INVISIBLE);
        mTopLayout.startAnimation(topHideAnimo);

        mBottomLayout.setVisibility(View.INVISIBLE);
        mBottomLayout.startAnimation(bottomHideAnimo);

        // if (getRequestedOrientation() ==
        // ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        sendEmptyMessageDelayed(HIDE_STATUS, STATUS_SHOW_DELAYED);
    }

    /**
     * 隐藏亮度或声音的提示
     */
    private void hideOperationView() {
        mOperationLayout.setVisibility(View.GONE);
        mOperationTv.setVisibility(View.GONE);
        isShowOperation = false;
    }

    /**
     * 显示亮度声音的提示
     */
    private void showOperationView() {
        mOperationLayout.setVisibility(View.VISIBLE);
        mOperationTv.setVisibility(View.VISIBLE);
        isShowOperation = true;
    }

    /**
     * 显示状态栏
     */
    private void showStatus() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 隐藏状态栏
     */
    private void hideStatus() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String error) {
        showToast(error);
    }

    @Override
    public void setPlayInfo(OldLiveVideoInfo oldLiveVideoInfo) {
        liveUrl = oldLiveVideoInfo.getData().getHls_url();
        runOnUiThread(() -> initPlayer(liveUrl));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            // 当手势结束，并且是单击结束时，控制器隐藏/显示
            if (isShowController) {
                hideControllerView();
            } else {
                showControllerView();
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // 滑动事件监听
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
            Display disp = getWindowManager().getDefaultDisplay();
            int windowWidth = disp.getWidth();
            int windowHeight = disp.getHeight();
            float newY = mOldY - y;
            if (Math.abs(newY) < 5) {
                if (!isGestureDetector) {
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }
            }
            isGestureDetector = true;
            if (mOldX > windowWidth / 2.0) {// 右边滑动 屏幕1/2
                onVolumeSlide(newY / windowHeight);
            } else if (mOldX < windowWidth / 2.0) {// 左边滑动 屏幕 1/2
                onBrightnessSlide(newY / windowHeight);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                if (++mDisplayAspectRatio > 2) {
                    mDisplayAspectRatio = 0;
                }
                mVideoView.setDisplayAspectRatio(DISPLAYASPECTRATIO[mDisplayAspectRatio]);
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            // 显示
            showOperationView();
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;
        if (index >= 10) {
            mOperationBg.setImageResource(R.mipmap.volmn_100);
        } else if (index >= 5 && index < 10) {
            mOperationBg.setImageResource(R.mipmap.volmn_60);
        } else if (index > 0 && index < 5) {
            mOperationBg.setImageResource(R.mipmap.volmn_30);
        } else {
            mOperationBg.setImageResource(R.mipmap.volmn_no);
        }
        mOperationTv.setText((int) (((double) index / mMaxVolume) * 100) + "%");
        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;
            // 显示
            showOperationView();
        }

        WindowManager.LayoutParams lpa = getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        getWindow().setAttributes(lpa);

        mOperationTv.setText((int) (lpa.screenBrightness * 100) + "%");
        if (lpa.screenBrightness * 100 >= 90) {
            mOperationBg.setImageResource(R.mipmap.light_100);
        } else if (lpa.screenBrightness * 100 >= 80 && lpa.screenBrightness * 100 < 90) {
            mOperationBg.setImageResource(R.mipmap.light_90);
        } else if (lpa.screenBrightness * 100 >= 70 && lpa.screenBrightness * 100 < 80) {
            mOperationBg.setImageResource(R.mipmap.light_80);
        } else if (lpa.screenBrightness * 100 >= 60 && lpa.screenBrightness * 100 < 70) {
            mOperationBg.setImageResource(R.mipmap.light_70);
        } else if (lpa.screenBrightness * 100 >= 50 && lpa.screenBrightness * 100 < 60) {
            mOperationBg.setImageResource(R.mipmap.light_60);
        } else if (lpa.screenBrightness * 100 >= 40 && lpa.screenBrightness * 100 < 50) {
            mOperationBg.setImageResource(R.mipmap.light_50);
        } else if (lpa.screenBrightness * 100 >= 30 && lpa.screenBrightness * 100 < 40) {
            mOperationBg.setImageResource(R.mipmap.light_40);
        } else if (lpa.screenBrightness * 100 >= 20 && lpa.screenBrightness * 100 < 20) {
            mOperationBg.setImageResource(R.mipmap.light_30);
        } else if (lpa.screenBrightness * 100 >= 10 && lpa.screenBrightness * 100 < 20) {
            mOperationBg.setImageResource(R.mipmap.light_20);
        }
    }

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;

        if (isShowOperation) {
            isShowOperation = false;
            mHandler.removeMessages(HIDE_OPERATION);
            sendEmptyMessageDelayed(HIDE_OPERATION, OPERATION_SHOW_DELAYED);
        }

        isGestureDetector = false;
    }

    private void setOptions(int code) {
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 15 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 15 * 1000);
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);// 直播模式
        options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);// 延时优化
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, code);// 优先硬件解码，失败后软解

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);// 不自动播放，手动调用start才开始播放
        mVideoView.setAVOptions(options);
    }

    private PLMediaPlayer.OnInfoListener mOnInfoListener = new PLMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(PLMediaPlayer plMediaPlayer, int what, int extra) {
            Log.e(TAG, "onInfo: " + what + ", " + extra);
            switch (what) {
                case PLMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:// 成功获取到视频的播放角度
                case PLMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    // if (mVideoView.isPlaying()) {
                    // mVideoView.pause();
                    // btnPlay.setImageResource(R.mipmap.icon_live_play);
                    // }
                    // // tvSpeed.setText("" + extra + "kb/s" + " ");
                    // loadingView.setVisibility(View.VISIBLE);
                    break;
                case PLMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:// 第一帧视频已成功渲染
                case PLMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    loadingView.setVisibility(View.GONE);
                    btnPlay.setImageResource(R.mipmap.icon_live_pause);
                    mVideoView.start();
                    break;
                default:
                    if (mVideoView != null) {
                        mVideoView.start();
                    }
            }
            return true;
        }
    };

    private PLMediaPlayer.OnErrorListener mOnErrorListener = (plMediaPlayer, errorCode) -> {
        boolean isNeedReconnect = false;
        Log.e(TAG, "Error happened, errorCode = " + errorCode);
        switch (errorCode) {
            case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                showToastTips("无效的 URL");
                break;
            case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                showToastTips("播放资源不存在");
                break;
            case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                showToastTips("服务器拒绝连接");
                break;
            case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                showToastTips("连接超时");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                showToastTips("空的播放列表");
                break;
            case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                showToastTips("与服务器连接断开");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                showToastTips("网络异常");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                showToastTips("未授权，播放一个禁播的流");
                break;
            case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                showToastTips("播放器准备超时");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                showToastTips("读取数据超时");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                setOptions(AVOptions.MEDIA_CODEC_SW_DECODE);
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                showToastTips("未知错误");
                break;
            default:
                showToastTips("unknown error !");
                sendReconnectMessage();
                break;
        }
        if (isNeedReconnect && !isStopReconnet) {
            sendReconnectMessage();
        }
        return true;
    };

    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            Log.d(TAG, "Play Completed !");
            showToastTips("Play Completed !");
            btnPlay.setImageResource(R.mipmap.icon_live_play);
        }
    };

    private PLMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new PLMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int precent) {
            // Log.d(TAG, "onBufferingUpdate: " + precent);
            // tvSpeed.setText(precent + "%");
            loadingView.setVisibility(View.GONE);
        }
    };

    private PLMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener = new PLMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(PLMediaPlayer plMediaPlayer) {
            Log.d(TAG, "onSeekComplete !");
        }
    };

    private PLMediaPlayer.OnPreparedListener mOnPreparedListener = new PLMediaPlayer.OnPreparedListener() {

        @Override
        public void onPrepared(PLMediaPlayer arg0) {

        }
    };

    private PLMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int videoSar, int videoDen) {
            Log.e(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height + ", sar = " + videoSar
                    + ", den = " + videoDen);
        }
    };

    private void showToastTips(final String tips) {
        if (mIsActivityPaused) {
            return;
        }
        if (System.currentTimeMillis() - lastTipsTime < 10000) {
            return;
        }
        runOnUiThread(() -> {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(mActivity, tips, Toast.LENGTH_SHORT);
            mToast.show();
            lastTipsTime = System.currentTimeMillis();
        });
    }

    private static final int MESSAGE_ID_RECONNECTING = 0x01;

    private void sendReconnectMessage() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActivityPaused = false;
        if (!TextUtils.isEmpty(liveUrl))
            initPlayer(liveUrl);

        if (mDanmakuView != null) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mToast = null;
        mIsActivityPaused = true;
        mVideoView.pause();
        btnPlay.setImageResource(R.mipmap.icon_live_play);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mVideoView.stopPlayback();
        if (mDanmakuView != null) {
            mDanmakuView.pause();
        }
    }

    private void initPermissionDialog() {
        Builder builder = new Builder(mActivity);
        View view = getLayoutInflater().inflate(R.layout.live_permission_dialog, null);
        TextView stop = (TextView) view.findViewById(R.id.dialog_stop);
        TextView play = (TextView) view.findViewById(R.id.dialog_play);
        stop.setOnClickListener(v -> {
            mVideoView.stopPlayback();
            mPermissionDialog.dismiss();
            isStopReconnet = true;
            btnPlay.setImageResource(R.mipmap.icon_live_play);
            isReconnect = false;
        });
        play.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            mPermissionDialog.dismiss();
            isReconnect = false;
            isFlowPermission = true;
            isStopReconnet = false;
            mVideoView.stopPlayback();
            initPlayer(liveUrl);
        });
        builder.setView(view);
        builder.setCancelable(false);
        mPermissionDialog = builder.create();
    }

    @Inject
    LivePlayPresenter mPresenter;

    @Override
    protected LivePlayPresenter createPresenter() {
        DaggerDYActivityComponent.builder().dYModule(new DYModule(this)).build().inject(this);
        return mPresenter;
    }

    @Override
    public void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        StatusBarUtil.setTransparent(this);

        lastTipsTime = System.currentTimeMillis();

        topShowAnimo = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, -0.5f, Animation.RELATIVE_TO_PARENT, 0);
        topShowAnimo.setDuration(CONTROLLER_ANIMO_TIME);

        bottomShowAnimo = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0);
        bottomShowAnimo.setDuration(CONTROLLER_ANIMO_TIME);

        topHideAnimo = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, -0.5f);
        topHideAnimo.setDuration(CONTROLLER_ANIMO_TIME);

        bottomHideAnimo = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.5f);
        bottomHideAnimo.setDuration(CONTROLLER_ANIMO_TIME);

        super.init();
    }

    @Override
    public void initViews() {
        initPermissionDialog();

        mLodingDialog = DialogUtils.getLodingDialog(this);
        mLodingDialog.show();
        tvRoomNo.setText(getIntent().getStringExtra("roomName"));

        initRecever();

        roomId = getIntent().getStringExtra("roomId");

        initDanmu();
    }

    private void initDanmu() {
        mDanmuProcess = new DanmuProcess(this, mDanmakuView, Integer.valueOf(roomId), 10);
        mDanmuProcess.start();
    }

    @Override
    protected void initData() {
        mPresenter.loadPlayInfo(roomId);
    }

    private void initRecever() {
        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    checkNet();
                    if ((isWifi || isMoble) && !isReconnect) {
                        mHandler.removeMessages(MESSAGE_ID_RECONNECTING);
                        if (mToast != null) {
                            mToast.cancel();
                        }
                        mToast = Toast.makeText(mActivity, "连接直播中", Toast.LENGTH_SHORT);
                        mToast.show();
                        if (!isFirstInitVideo) {
                            initPlayer(liveUrl);
                        }
                    }
                }
            }
        };
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public int setLayoutRes() {
        // TODO Auto-generated method stub
        return R.layout.activity_phone_live_detail;
    }

    private void checkNet() {
        isMoble = NetworkUtils.isMobileConnected(this);
        isWifi = NetworkUtils.isWifiConnected(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        unregisterReceiver(mReceiver);
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }
}
