package com.qzlink.agorasipdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_set).setOnClickListener(this);
        findViewById(R.id.btn_phone).setOnClickListener(this);
        findViewById(R.id.btn_sip).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_phone:
                startActivity(new Intent(MainActivity.this, CallPhoneActivity.class));
                break;
            case R.id.btn_sip:
                startActivity(new Intent(MainActivity.this, CallSipActivity.class));
                break;
            case R.id.btn_set:
                startActivity(new Intent(MainActivity.this, CallSetActivity.class));
                break;
        }
    }
}
