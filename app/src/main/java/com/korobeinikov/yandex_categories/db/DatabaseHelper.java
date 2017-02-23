package com.korobeinikov.yandex_categories.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "yandex.money";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CATEGORIES = "categories";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_PARENT_ID = "parent_id";

    private static final String CREATE_TABLE_CATEGORIES =
            " CREATE TABLE " + TABLE_CATEGORIES +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TITLE + " TEXT NOT NULL, "
                    + COLUMN_CATEGORY_ID + " INT, "
                    + COLUMN_PARENT_ID + " INT" + ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        onCreate(db);
    }

}
