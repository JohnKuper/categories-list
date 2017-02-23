package com.korobeinikov.yandex_categories.model;

import android.database.Cursor;

import java.util.ArrayList;

import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.CATEGORY_ID;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.PARENT_ID;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.TITLE;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class Category {

    private int mCategoryId;
    private int mParentId;
    private String mTitle;
    private ArrayList<Category> mSubs;

    public static Category fromCursor(Cursor cursor) {
        Category category = new Category();
        category.setCategoryId(cursor.getInt(cursor.getColumnIndex(CATEGORY_ID)));
        category.setParentId(cursor.getInt(cursor.getColumnIndex(PARENT_ID)));
        category.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
        return category;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
    }

    public int getParentId() {
        return mParentId;
    }

    public void setParentId(int parentId) {
        mParentId = parentId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ArrayList<Category> getSubs() {
        return mSubs;
    }

    public boolean hasSubs() {
        return mSubs != null && mSubs.size() > 0;
    }

    public void setSubs(ArrayList<Category> subs) {
        mSubs = new ArrayList<>(subs);
    }
}
