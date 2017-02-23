package com.korobeinikov.yandex_categories.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.korobeinikov.yandex_categories.model.CategoriesContract;

import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.CONTENT_URI;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.TABLE_NAME;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesContentProvider extends ContentProvider {
    private static final int URI_CATEGORIES = 1;
    private static final int URI_CATEGORY = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CategoriesContract.AUTHORITY, TABLE_NAME, URI_CATEGORIES);
        sUriMatcher.addURI(CategoriesContract.AUTHORITY, TABLE_NAME + "/#", URI_CATEGORY);
    }

    private DatabaseHelper mDBHelper;
    private ContentResolver mResolver;

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onCreate() {
        mDBHelper = new DatabaseHelper(getContext());
        mResolver = getContext().getContentResolver();
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String type;
        switch (sUriMatcher.match(uri)) {
            case URI_CATEGORIES:
                type = CategoriesContract.Categories.CONTENT_TYPE_LIST;
                break;
            case URI_CATEGORY:
                type = CategoriesContract.Categories.CONTENT_TYPE_ITEM;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long insertID = db.insert(TABLE_NAME, null, values);
        Uri resultUri = ContentUris.withAppendedId(CONTENT_URI, insertID);
        mResolver.notifyChange(resultUri, null, false);
        return resultUri;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(mResolver, uri);
        return cursor;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
