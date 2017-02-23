package com.korobeinikov.yandex_categories.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.korobeinikov.yandex_categories.model.CategoriesContract;
import com.korobeinikov.yandex_categories.model.Category;
import com.korobeinikov.yandex_categories.ui.CategoriesFragment;

import java.util.List;

import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.CONTENT_URI;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesPersister {

    private ContentResolver mResolver;

    public CategoriesPersister(Context context) {
        mResolver = context.getContentResolver();
    }

    public void putToCache(List<Category> categories) {
        insertRecursively(categories, CategoriesFragment.ID_ROOT_CATEGORY);
    }

    @SuppressWarnings("ConstantConditions")
    private void insertRecursively(List<Category> categories, long parentId) {
        for (Category category : categories) {
            ContentValues values = createValues(category, parentId);
            Uri uri = mResolver.insert(CONTENT_URI, values);
            int insertId = Integer.valueOf(uri.getLastPathSegment());
            if (category.hasSubs()) {
                insertRecursively(category.getSubs(), insertId);
            }
        }
    }

    private ContentValues createValues(Category category, long parentId) {
        ContentValues values = new ContentValues();
        values.put(CategoriesContract.Categories.TITLE, category.getTitle());
        values.put(CategoriesContract.Categories.CATEGORY_ID, category.getCategoryId());
        values.put(CategoriesContract.Categories.PARENT_ID, parentId);
        return values;
    }
}
