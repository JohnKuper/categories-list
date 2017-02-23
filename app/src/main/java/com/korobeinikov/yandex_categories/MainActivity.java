package com.korobeinikov.yandex_categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.korobeinikov.yandex_categories.db.CategoriesProvider;
import com.korobeinikov.yandex_categories.model.Category;
import com.korobeinikov.yandex_categories.network.CategoriesRequester;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button requestBtn = (Button) findViewById(R.id.btnMakeRequest);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriesRequester.class);
                startService(intent);
            }
        });

        findViewById(R.id.btnGetCategories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriesProvider provider = new CategoriesProvider(MainActivity.this);
                ArrayList<Category> categories = provider.getByID(-1);
            }
        });
    }
}
