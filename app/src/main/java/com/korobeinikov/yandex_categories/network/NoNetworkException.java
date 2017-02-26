package com.korobeinikov.yandex_categories.network;

import java.io.IOException;

/**
 * Created by Dmitriy Korobeinikov on 26.02.2017.
 */

public class NoNetworkException extends IOException {

    public NoNetworkException() {
        super("Please check your network connection");
    }
}
