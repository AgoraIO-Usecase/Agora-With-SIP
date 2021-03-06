package com.qzlink.agorasipdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CallSetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etIP;
    private EditText etPort;
    private EditText etAgoraAppid;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_set);

        etIP = findViewById(R.id.et_ip);
        etPort = findViewById(R.id.et_port);
        etAgoraAppid = findViewById(R.id.et_agora_appid);
        btnConfirm = findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(this);

        setAllSaveDataToView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                setAllDataSave();
                break;
        }
    }

    private void setAllSaveDataToView() {
        etIP.setText(Constants.getIp());
        etPort.setText(Constants.getPort());
        etAgoraAppid.setText(Constants.getAgoraAppid());
    }

    private void setAllDataSave() {
        String phoneAddress = etIP.getText().toString();
        String sipAddress = etPort.getText().toString();
        String appId = etAgoraAppid.getText().toString();

        Constants.setIp(phoneAddress);
        Constants.setPort(sipAddress);
        Constants.setAgoraAppid(appId);

        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }
}
