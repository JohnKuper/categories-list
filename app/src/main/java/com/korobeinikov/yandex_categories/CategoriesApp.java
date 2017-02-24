package com.korobeinikov.yandex_categories;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesApp extends Application {

    private static CategoriesApp sCategoriesApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sCategoriesApp = this;
        Stetho.initializeWithDefaults(this);
    }

    public static Context getAppContext() {
        return sCategoriesApp.getApplicationContext();
    }
}
