package com.korobeinikov.yandex_categories.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.korobeinikov.yandex_categories.R;

import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.TITLE;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesCursorAdapter extends CursorAdapter {

    public CategoriesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_category, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView categoryTitle = (TextView) view.findViewById(R.id.tvCategoryTitle);
        categoryTitle.setText(getCategoryTitle(cursor));
    }

    public String getCategoryTitle(int position) {
        return getCategoryTitle((Cursor) getItem(position));
    }

    private String getCategoryTitle(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(TITLE));
    }
}
