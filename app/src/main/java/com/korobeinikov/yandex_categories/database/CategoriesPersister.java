package com.korobeinikov.yandex_categories.database;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import com.korobeinikov.yandex_categories.model.CategoriesContract;
import com.korobeinikov.yandex_categories.model.Category;

import java.util.ArrayList;
import java.util.List;

import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.CATEGORY_ID;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.CONTENT_URI;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.PARENT_ID;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.TITLE;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesPersister {
    private static final String TAG = "CategoriesPersister";

    private ContentResolver mResolver;

    public CategoriesPersister(Context context) {
        mResolver = context.getContentResolver();
    }

    public void putToCache(List<Category> categories) {
        try {
            ArrayList<ContentProviderOperation> operations = getOperations(categories, -1);
            mResolver.applyBatch(CategoriesContract.AUTHORITY, operations);
        } catch (RemoteException | OperationApplicationException e) {
            Log.e(TAG, "Error during caching categories into database", e);
        }
    }

    private ArrayList<ContentProviderOperation> getOperations(List<Category> categories, int operationIndex) {
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        int numOfInsertion = operationIndex;
        for (Category category : categories) {
            operations.add(createOperation(category, operationIndex));
            numOfInsertion++;
            if (category.hasSubs()) {
                ArrayList<ContentProviderOperation> subsOperations = getOperations(category.getSubs(), numOfInsertion);
                operations.addAll(subsOperations);
                numOfInsertion += subsOperations.size();
            }
        }
        return operations;
    }

    private ContentProviderOperation createOperation(Category category, int operationIndex) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(CONTENT_URI);
        builder.withValues(createValues(category));
        if (operationIndex >= 0) {
            builder.withValueBackReference(PARENT_ID, operationIndex);
        }
        return builder.build();
    }

    private ContentValues createValues(Category category) {
        ContentValues values = new ContentValues();
        values.put(TITLE, category.getTitle());
        values.put(CATEGORY_ID, category.getCategoryId());
        return values;
    }

    public boolean isDatabaseEmpty() {
        Cursor cursor = mResolver.query(CONTENT_URI, null, null, null, null);
        boolean isEmpty = true;
        if (cursor != null && cursor.getCount() > 0) {
            isEmpty = false;
            cursor.close();
        }
        return isEmpty;
    }
}
