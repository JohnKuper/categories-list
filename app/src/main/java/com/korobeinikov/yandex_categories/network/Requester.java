package com.korobeinikov.yandex_categories.network;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public abstract class Requester<T> {

    protected final OkHttpClient mClient = new OkHttpClient();
    protected ResponseListener<T> mResponseListener;

    public Requester(ResponseListener<T> responseListener) {
        mResponseListener = responseListener;
    }
}
