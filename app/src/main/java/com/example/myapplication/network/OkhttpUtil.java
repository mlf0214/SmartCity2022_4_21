package com.example.myapplication.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtil {
    private OkHttpClient okHttpClient;
    private Okhttplim okhttplim;
    private JSONObject jsonObject;


    public OkhttpUtil() {
        okHttpClient=new OkHttpClient
                .Builder()
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50,TimeUnit.SECONDS)
                .connectTimeout(50,TimeUnit.SECONDS)
                .callTimeout(50,TimeUnit.SECONDS)
                .build();
        jsonObject=new JSONObject();
    }
    public void setOkhttplim(Okhttplim okhttplim){
        this.okhttplim=okhttplim;
    }
    public void setGet(String url,String token){
        Request request=null;
        if (token.equals("")){
            request=new Request.Builder()
                    .url(url)
                    .get()
                    .build();
        }else {
            request=new Request.Builder()
                    .url(url).addHeader("Authorization",token)
                    .get()
                    .build();
        }
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                okhttplim.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                okhttplim.onResponse(call, response);
                okhttplim.getString(string);
            }
        });

    }
    public void setPost(String url,String token,String... strings){
        Request request=null;
        for (int i = 0; i < strings.length; i++) {
            if (i%2==0){
                try {
                    jsonObject.put(strings[i],strings[i+1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        if (token.equals("")){
            request=new Request.Builder()
                    .post(requestBody)
                    .url(url)
                    .build();
        }else {
            request=new Request.Builder()
                    .post(requestBody)
                    .addHeader("Authorization",token)
                    .url(url)
                    .build();
        }
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                okhttplim.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                okhttplim.onResponse(call, response);
                String string = response.body().string();
                okhttplim.getString(string);
            }
        });
    }
    public void setPut(String url,String token,String... strings){
        Request request=null;
        for (int i = 0; i < strings.length; i++) {
            if (i%2==0){
                try {
                    jsonObject.put(strings[i],strings[i+1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        if (token.equals("")){
            request=new Request.Builder()
                    .put(requestBody)
                    .url(url)
                    .build();
        }else {
            request=new Request.Builder()
                    .put(requestBody)
                    .addHeader("Authorization",token)
                    .url(url)
                    .build();
        }
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                okhttplim.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                okhttplim.onResponse(call, response);
                String string = response.body().string();
                okhttplim.getString(string);
            }
        });
    }
}
