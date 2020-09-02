package com.qzlink.agorasipdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.IOException;
import java.util.Arrays;

import javax.microedition.khronos.egl.EGLContext;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.AgoraVideoFrame;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.functions.Action1;

public class ConverseActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = ConverseActivity.class.getSimpleName();

    public static final String KEY_INTENT_CALL_DATA = "key_intent_call_data";

    private TextView tvHangUp;

    private FrameLayout mLocalContainer;
    private RelativeLayout mRemoteContainer;
    private SurfaceView mLocalView;
    private SurfaceView mRemoteView;

    protected RtcEngine mRtcEngine;

    private CallDataBean callDataBean;

    protected final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupRemoteVideo(uid);
                }
            });
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            Log.e(TAG, "onUserOffline uid = " + uid + "  reason = " + reason);
            finish();
        }

        @Override
        public void onUserMuteVideo(final int uid, final boolean muted) {
        }

        @Override
        public void onUserJoined(int userId, int elapsed) {
            super.onUserJoined(userId, elapsed);
            Log.e(TAG, "onUserJoined  uid = " + userId + "  elapsed = " + elapsed);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converse);

        tvHangUp = findViewById(R.id.tv_hang_up);

        mLocalContainer = findViewById(R.id.local_video_view_container);
        mRemoteContainer = findViewById(R.id.remote_video_view_container);

        tvHangUp.setOnClickListener(this);

        callDataBean = (CallDataBean) getIntent().getSerializableExtra(KEY_INTENT_CALL_DATA);

        if (RxPermissions.getInstance(this).isGranted(android.Manifest.permission.RECORD_AUDIO) &&
                RxPermissions.getInstance(this).isGranted(android.Manifest.permission.CAMERA) &&
                RxPermissions.getInstance(this).isGranted(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            initAgoraEngine();
        } else {
            RxPermissions.getInstance(this).request(android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted)
                                initAgoraEngine();
                        }
                    });
        }
    }

    private void initAgoraEngine() {
        initializeAgoraEngine();

        joinChannel();

        if (null != mRtcEngine) {
            mRtcEngine.setEnableSpeakerphone(false);
            mRtcEngine.setAudioProfile(2, 0);
            mRtcEngine.setLogFilter(0);//不输出任何日志
            mRtcEngine.setInEarMonitoringVolume(1);
        }
    }

    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(this, Constants.getAgoraAppid(), mRtcEventHandler);
        } catch (Exception e) {
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    private void setupRemoteVideo(int uid) {
        // Only one remote video view is available for this
        // tutorial. Here we check if there exists a surface
        // view tagged as this uid.
        int count = mRemoteContainer.getChildCount();
        View view = null;
        for (int i = 0; i < count; i++) {
            View v = mRemoteContainer.getChildAt(i);
            if (v.getTag() instanceof Integer && ((int) v.getTag()) == uid) {
                view = v;
            }
        }

        if (view != null) {
            return;
        }

        mRemoteView = RtcEngine.CreateRendererView(getBaseContext());
        mRemoteContainer.addView(mRemoteView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(mRemoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        mRemoteView.setTag(uid);
    }

    /**
     * 加入房间
     */
    protected void joinChannel() {
        if (mRtcEngine != null && callDataBean != null) {

            String token = null;
            if (!TextUtils.isEmpty(callDataBean.getToken()))
                token = callDataBean.getToken();

            int uid = 0;
            if (!TextUtils.isEmpty(callDataBean.getUid()))
                uid = Integer.parseInt(callDataBean.getUid());

            mRtcEngine.joinChannel(token, callDataBean.getRoomId(), "Extra Optional Data", uid);
        }
    }

    /**
     * 退出房间
     */
    protected void leaveChannel() {
        if (mRtcEngine != null)
            mRtcEngine.leaveChannel();

        Toast.makeText(ConverseActivity.this, "退出房间", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_hang_up:
                leaveChannel();
                reqHangUp();
                break;
        }
    }

    private void reqHangUp() {
        Log.e(TAG, "reqHangUp callDataBean = " + callDataBean);
        if (callDataBean == null)
            return;

        String url = Constants.requestKillCallUrl + "?roomid=" + callDataBean.getRoomId() +
                "&caller=" + callDataBean.getCaller() + "&callee=" + callDataBean.getPhone();

        RequestHelper helper = new RequestHelper(url);

        helper.request(new RequestHelper.ResponseListener() {
            @Override
            public void onBack(String back) {
                Log.e(TAG, "response = " + back);

                JSONObject json = JSONObject.parseObject(back);

                if (json.getString("code").equals("000000")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ConverseActivity.this, "挂断", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RtcEngine.destroy();
    }
}
