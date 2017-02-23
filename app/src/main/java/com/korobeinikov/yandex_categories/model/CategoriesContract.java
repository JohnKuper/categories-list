package com.korobeinikov.yandex_categories.model;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public final class CategoriesContract {
    public static final String AUTHORITY = "com.korobeinikov.yandex.categories";

    public static class Categories implements BaseColumns {
        public static final String TABLE_NAME = "categories";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_TYPE_LIST = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;

        public static final String TITLE = "title";
        public static final String CATEGORY_ID = "category_id";
        public static final String PARENT_ID = "parent_id";
    }
}
