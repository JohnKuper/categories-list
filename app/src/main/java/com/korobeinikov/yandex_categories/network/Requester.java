package com.korobeinikov.yandex_categories.network;

import com.korobeinikov.yandex_categories.CategoriesApp;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public abstract class Requester<T> {

    protected ResponseListener<T> mResponseListener;

    public Requester(ResponseListener<T> responseListener) {
        mResponseListener = responseListener;
    }

    protected void executeAsync(Request request) {
        CategoriesApp.getNetworkClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                onRequestFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    onRequestFailure(response.request(), new IOException("Request failed with status code:" + response.code()));
                } else {
                    onSuccessResponse(response);
                }
            }
        });
    }

    protected abstract void onRequestFailure(Request request, IOException e);

    protected abstract void onSuccessResponse(Response response);
}
