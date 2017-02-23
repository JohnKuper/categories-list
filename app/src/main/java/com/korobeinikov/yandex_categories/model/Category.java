package com.korobeinikov.yandex_categories.model;

import java.util.ArrayList;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class Category {

    private int mCategoryId;
    private String mTitle;
    private ArrayList<Category> mSubs;

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
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
