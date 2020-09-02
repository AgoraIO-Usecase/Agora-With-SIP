package com.qzlink.agorasipdemo;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author wang.peng
 * @Email 1929774468@qq.com
 * @Date 2020/9/2
 * @Description
 */
public class RequestHelper {

    private OkHttpClient.Builder builder;
    private OkHttpClient httpClient;
    private Request request;
    private Call call;

    public RequestHelper(String url) {
        builder = new OkHttpClient.Builder();
        httpClient = builder.build();

        request = new Request.Builder()
                .url(url)
                .get()
                .build();

        call = httpClient.newCall(request);
    }

    public void request(final ResponseListener listener) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null)
                    listener.onBack(buildErrorBack());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String date = response.body().string();
                if (listener != null)
                    listener.onBack(date);
            }
        });
    }


    public interface ResponseListener {
        void onBack(String back);
    }

    private String buildErrorBack() {
        JSONObject errStr = new JSONObject();

        errStr.put("errcode", "-1");
        errStr.put("errmsg", "net error");

        return errStr.toString();
    }
}
