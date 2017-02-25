package com.korobeinikov.yandex_categories;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.korobeinikov.yandex_categories.network.NetworkAvailabilityInterceptor;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesApp extends Application {

    private static CategoriesApp sCategoriesApp;
    private static OkHttpClient mNetworkClient;

    @Override
    public void onCreate() {
        super.onCreate();
        sCategoriesApp = this;
        setupNetworkClient();
        Stetho.initializeWithDefaults(this);
    }

    private void setupNetworkClient() {
        mNetworkClient = new OkHttpClient();
        mNetworkClient.interceptors().add(new NetworkAvailabilityInterceptor());
    }

    public static Context getAppContext() {
        return sCategoriesApp.getApplicationContext();
    }

    public static OkHttpClient getNetworkClient() {
        return mNetworkClient;
    }
}
