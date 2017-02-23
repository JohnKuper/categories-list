package com.korobeinikov.yandex_categories;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
