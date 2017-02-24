package com.korobeinikov.yandex_categories.network;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public interface ResponseListener<T> {
    void onSuccess(T object);

    void onFailure(Throwable e);
}
