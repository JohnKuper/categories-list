package com.korobeinikov.yandex_categories.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.korobeinikov.yandex_categories.R;
import com.korobeinikov.yandex_categories.db.CategoriesPersister;
import com.korobeinikov.yandex_categories.network.CategoriesRequester;
import com.korobeinikov.yandex_categories.ui.CategoriesFragment.OnCategoryClickListener;


public class MainActivity extends AppCompatActivity implements OnCategoryClickListener {
    private static final long ID_ROOT_CATEGORY = -1;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addInitialFragment();

        findViewById(R.id.btnMakeRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriesRequester.class);
                startService(intent);
            }
        });

        findViewById(R.id.btnGetCategories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriesPersister provider = new CategoriesPersister(MainActivity.this);
            }
        });
    }

    private void addInitialFragment() {
        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = CategoriesFragment.newInstance(getString(R.string.root_categories_title), ID_ROOT_CATEGORY);
            mFragmentManager.beginTransaction().add(R.id.container, fragment, null).commit();
        }
    }

    @Override
    public void onCategoryClick(@NonNull String title, long id) {
        CategoriesFragment fragment = CategoriesFragment.newInstance(title, id);
        mFragmentManager.beginTransaction().replace(R.id.container, fragment, null).addToBackStack(null).commit();
    }
}
