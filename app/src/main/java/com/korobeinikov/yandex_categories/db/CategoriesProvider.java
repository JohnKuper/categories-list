package com.korobeinikov.yandex_categories.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.korobeinikov.yandex_categories.model.Category;

import java.util.ArrayList;
import java.util.List;

import static com.korobeinikov.yandex_categories.db.DatabaseHelper.COLUMN_CATEGORY_ID;
import static com.korobeinikov.yandex_categories.db.DatabaseHelper.COLUMN_PARENT_ID;
import static com.korobeinikov.yandex_categories.db.DatabaseHelper.COLUMN_TITLE;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesProvider {

    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDBHelper;

    public CategoriesProvider(Context context) {
        mDBHelper = new DatabaseHelper(context);
    }

    public void openDB() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mDBHelper.getWritableDatabase();
        }
    }

    public void closeDB() {
        mDBHelper.close();
    }

    public void putToCache(List<Category> categories) {
        openDB();
        insertRecursively(categories, -1);
        closeDB();
    }

    private void insertRecursively(List<Category> categories, long parentId) {
        for (Category category : categories) {
            ContentValues values = createValues(category, parentId);
            long insertId = mDatabase.insert(DatabaseHelper.TABLE_CATEGORIES, null, values);
            if (category.hasSubs()) {
                insertRecursively(category.getSubs(), insertId);
            }
        }
    }

    private ContentValues createValues(Category category, long parentId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, category.getTitle());
        values.put(COLUMN_CATEGORY_ID, category.getCategoryId());
        values.put(COLUMN_PARENT_ID, parentId);
        return values;
    }

    public ArrayList<Category> getByID(long parentId) {
        openDB();

        ArrayList<Category> result = new ArrayList<>();
        String whereClause = COLUMN_PARENT_ID + " = " + parentId;
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_CATEGORIES, null, whereClause, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result.add(Category.fromCursor(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        closeDB();
        return result;
    }
}
