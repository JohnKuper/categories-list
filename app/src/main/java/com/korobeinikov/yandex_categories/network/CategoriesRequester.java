package com.korobeinikov.yandex_categories.network;

import android.util.Log;

import com.korobeinikov.yandex_categories.BuildConfig;
import com.korobeinikov.yandex_categories.model.Category;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesRequester extends Requester<List<Category>> {
    private static final String TAG = "CategoriesRequester";

    public CategoriesRequester(ResponseListener<List<Category>> responseListener) {
        super(responseListener);
    }

    public void requestCategories() {
        final Request request = new Request.Builder().url(BuildConfig.CATEGORIES_URL).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mResponseListener.onFailure(e);
                Log.e(TAG, "Request with url=" + request.url() + " failed", e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    mResponseListener.onFailure(new IOException("Request failed with status code:" + response.code()));
                } else {
                    proceedSuccessResponse(response);
                }
            }
        });
    }

    private void proceedSuccessResponse(Response response) {
        try {
            ArrayList<Category> categories = CategoriesParser.parse(response);
            mResponseListener.onSuccess(categories);
        } catch (IOException | JSONException e) {
            mResponseListener.onFailure(e);
            Log.e(TAG, "Response parsing failed", e);
        }
    }
}
