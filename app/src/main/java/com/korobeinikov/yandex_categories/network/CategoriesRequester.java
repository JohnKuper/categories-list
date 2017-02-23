package com.korobeinikov.yandex_categories.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.korobeinikov.yandex_categories.BuildConfig;
import com.korobeinikov.yandex_categories.db.CategoriesPersister;
import com.korobeinikov.yandex_categories.model.Category;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dmitriy Korobeinikov on 21.02.2017
 */

public class CategoriesRequester extends IntentService {
    private static final String TAG = "CategoriesRequester";

    public CategoriesRequester() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        JSONArray categoriesJSON = convertToJSON(getResponse());
        putToCache(parse(categoriesJSON));
    }

    private Response getResponse() {
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(BuildConfig.CATEGORIES_URL).build();
            response = client.newCall(request).execute();
        } catch (IOException e) {
            Log.e(TAG, "Error during request for categories", e);
        }
        return response;
    }

    private JSONArray convertToJSON(Response response) {
        JSONArray array = null;
        String body = null;
        try {
            body = response.body().string();
            array = new JSONArray(body);
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error during parsing categories response: " + body, e);
        }
        return array;
    }

    private ArrayList<Category> parse(JSONArray categories) {
        CategoriesParser parser = new CategoriesParser();
        return parser.parseCategories(categories);
    }

    private void putToCache(ArrayList<Category> categories) {
        CategoriesPersister persister = new CategoriesPersister(getApplicationContext());
        persister.putToCache(categories);
    }
}
