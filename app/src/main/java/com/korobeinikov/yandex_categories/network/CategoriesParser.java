package com.korobeinikov.yandex_categories.network;

import android.util.Log;

import com.korobeinikov.yandex_categories.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesParser {
    private static final String TAG = "CategoriesParser";

    private static final String JSON_CATEGORY_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SUBS = "subs";

    public ArrayList<Category> parseCategories(JSONArray jsonArray) {
        ArrayList<Category> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Category category = new Category();
                if (object.has(JSON_CATEGORY_ID)) {
                    category.setCategoryId(object.getInt(JSON_CATEGORY_ID));
                }
                if (object.has(JSON_TITLE)) {
                    category.setTitle(object.getString(JSON_TITLE));
                }
                if (object.has(JSON_SUBS)) {
                    category.setSubs(parseCategories(object.getJSONArray(JSON_SUBS)));
                }
                result.add(category);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error during categories parsing", e);
        }
        return result;
    }
}
