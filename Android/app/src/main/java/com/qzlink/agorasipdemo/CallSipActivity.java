package com.qzlink.agorasipdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CallSipActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etRoomId;
    private EditText etCaller;
    private EditText etPhone;
    private EditText etGw;
    private ImageView ivRefresh;
    private Button btnAudioCall;
    private Button btnVideoCall;
    private TextView tvStatus;

    private CallDataBean callDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_sip);

        etRoomId = findViewById(R.id.et_room_id);
        etCaller = findViewById(R.id.et_caller);
        etPhone = findViewById(R.id.et_phone);
        etGw = findViewById(R.id.et_gw);
        ivRefresh = findViewById(R.id.iv_refresh);
        btnAudioCall = findViewById(R.id.btn_audio_call);
        btnVideoCall = findViewById(R.id.btn_video_call);
        tvStatus = findViewById(R.id.tv_status);

        ivRefresh.setOnClickListener(this);
        btnAudioCall.setOnClickListener(this);
        btnVideoCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_refresh:
                randomRoomId();
                break;
            case R.id.btn_audio_call:
                callPhoneOut(false);
                break;
            case R.id.btn_video_call:
                callPhoneOut(true);
                break;
        }
    }

    private void randomRoomId() {
        String roomId = String.valueOf(Math.abs(new Random().nextLong()));
        etRoomId.setText(roomId);
    }

    private void callPhoneOut(boolean video) {
        String roomId = etRoomId.getText().toString();

        if (TextUtils.isEmpty(roomId)) {
            Toast.makeText(this, "Room id 是空的", Toast.LENGTH_SHORT).show();
            return;
        }

        requestCallInfo(roomId, video);
    }

    private void requestCallInfo(final String roomId, final boolean video) {
        final String url = Constants.requestCallInfoUrl + "?roomid=" + roomId;

        RequestHelper reqCallInfo = new RequestHelper(url);

        reqCallInfo.request(new RequestHelper.ResponseListener() {
            @Override
            public void onBack(String back) {
                Log.e("PWDebug", "requestCallInfo back = " + back);
                try {
                    JSONObject json = JSONObject.parseObject(back);
                    String errcode = json.getString("errcode");

                    if (!TextUtils.isEmpty(errcode) && errcode.equals("0")) {
                        final String uid = json.getString("uid");
                        final String roomID = json.getString("roomID");
                        final String token = json.getString("token");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                requestCall(roomID, token, uid, video);
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.e("PWDebug", "reqCallInfo err = " + e.getMessage());
                }
            }
        });
    }

    private void requestCall(String roomId, String token, String uid, boolean video) {
        tvStatus.setText("Call...");

        String caller = etCaller.getText().toString();
        String phone = etPhone.getText().toString();
        String gw = etGw.getText().toString();

        if (TextUtils.isEmpty(caller)) {
            Toast.makeText(this, "Caller 是空的", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Phone 是空的", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(gw)) {
            Toast.makeText(this, "Gw 是空的", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Constants.requestSipCallUrl + "?caller=" + caller + "&room_id=" + roomId +
                "&phone=" + phone + "&gw=" + gw;

        if (video)
            url = url + "&callType=VIDEO";

        callDataBean = new CallDataBean();
        callDataBean.setRoomId(roomId);
        callDataBean.setCaller(caller);
        callDataBean.setPhone(phone);
        callDataBean.setGw(gw);
        callDataBean.setToken(token);
        callDataBean.setUid(uid);
        callDataBean.setVideo(video);

        Log.e("PWDebug", "url = " + url);

        RequestHelper helper = new RequestHelper(url);

        helper.request(new RequestHelper.ResponseListener() {
            @Override
            public void onBack(String back) {
                try {
                    JSONObject json = JSONObject.parseObject(back);
                    if (json.getString("code").equals("000000")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvStatus.setText("");
                                Toast.makeText(CallSipActivity.this, "加入房间", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CallSipActivity.this, ConverseActivity.class);
                                intent.putExtra(ConverseActivity.KEY_INTENT_CALL_DATA, callDataBean);
                                startActivity(intent);
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.e("PWDebug", "requestCall err = " + e.getMessage());
                }
            }
        });
    }
}
