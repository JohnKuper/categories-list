package com.korobeinikov.yandex_categories.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.CATEGORY_ID;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.PARENT_ID;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.TABLE_NAME;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.TITLE;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "yandex.money.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_CATEGORIES =
            " CREATE TABLE " + TABLE_NAME +
                    "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TITLE + " TEXT NOT NULL, "
                    + CATEGORY_ID + " INT, "
                    + PARENT_ID + " INT" + ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
