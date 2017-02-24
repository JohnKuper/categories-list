package com.korobeinikov.yandex_categories.network;

import com.korobeinikov.yandex_categories.model.Category;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesParser {

    private static final String JSON_CATEGORY_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SUBS = "subs";

    public static ArrayList<Category> parse(Response response) throws IOException, JSONException {
        CategoriesParser parser = new CategoriesParser();
        JSONArray jsonArray = parser.convertToJSON(response);
        return parser.parseRecursively(jsonArray);
    }

    private ArrayList<Category> parseRecursively(JSONArray jsonArray) throws JSONException {
        ArrayList<Category> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Category category = new Category();
            category.setTitle(object.getString(JSON_TITLE));
            if (object.has(JSON_CATEGORY_ID)) {
                category.setCategoryId(object.getInt(JSON_CATEGORY_ID));
            }
            if (object.has(JSON_SUBS)) {
                category.setSubs(parseRecursively(object.getJSONArray(JSON_SUBS)));
            }
            result.add(category);
        }
        return result;
    }

    private JSONArray convertToJSON(Response response) throws IOException, JSONException {
        String body = response.body().string();
        return new JSONArray(body);
    }
}
