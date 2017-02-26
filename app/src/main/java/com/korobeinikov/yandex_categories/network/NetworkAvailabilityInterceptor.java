package com.korobeinikov.yandex_categories.network;

import com.korobeinikov.yandex_categories.CategoriesApp;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static com.korobeinikov.yandex_categories.network.NetworkMonitor.isNetworkAvailable;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class NetworkAvailabilityInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (isNetworkAvailable(CategoriesApp.getAppContext())) {
            return chain.proceed(chain.request());
        } else {
            throw new NoNetworkException();
        }
    }
}
