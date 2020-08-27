package com.qzlink.agorasipdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.IOException;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
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

    protected RtcEngine mRtcEngine;

    private CallDataBean callDataBean;

    protected final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
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

        tvHangUp.setOnClickListener(this);

        callDataBean = (CallDataBean) getIntent().getSerializableExtra(KEY_INTENT_CALL_DATA);

        initAgoraEngine();

        joinChannel();
    }

    private void initAgoraEngine() {
        initializeAgoraEngine();
        if (null != mRtcEngine) {
            mRtcEngine.setEnableSpeakerphone(false);
            mRtcEngine.setAudioProfile(2, 0);
            mRtcEngine.setLogFilter(0);//不输出任何日志
            mRtcEngine.setInEarMonitoringVolume(1);
        }
    }

    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(this, getString(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    /**
     * 加入房间
     */
    protected void joinChannel() {
        if (mRtcEngine != null && callDataBean != null) {
            if (RxPermissions.getInstance(this).isGranted(android.Manifest.permission.RECORD_AUDIO)) {
                // if you do not specify the uid, we will generate the uid for you
                mRtcEngine.joinChannel(getString(R.string.agora_access_token), callDataBean.getRoomId(), "Extra Optional Data", 0);
            } else {
                RxPermissions.getInstance(this).request(android.Manifest.permission.RECORD_AUDIO)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean granted) {
                                if (granted)
                                    // if you do not specify the uid, we will generate the uid for you
                                    mRtcEngine.joinChannel(null, callDataBean.getRoomId(), "Extra Optional Data", 0);
                            }
                        });
            }
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

        request(url);
    }

    private void request(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        OkHttpClient httpClient = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "e = " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String date = response.body().string();
                JSONObject json = JSONObject.parseObject(date);

                Log.e(TAG, "response = " + date);
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
