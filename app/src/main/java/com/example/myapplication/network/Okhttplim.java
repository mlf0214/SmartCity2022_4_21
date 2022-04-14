package com.example.myapplication.network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface Okhttplim {
    void onFailure(Call call, IOException e);
    void onResponse(Call call, Response response) throws IOException;
    void getString(String s);
}
