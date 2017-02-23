package com.korobeinikov.yandex_categories.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.korobeinikov.yandex_categories.R;
import com.korobeinikov.yandex_categories.ui.CategoriesFragment.OnCategoryClickListener;

import static com.korobeinikov.yandex_categories.ui.CategoriesFragment.ID_ROOT_CATEGORY;

public class MainActivity extends AppCompatActivity implements OnCategoryClickListener {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addInitialFragment();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                clearBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearBackStack() {
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onCategoryClick(@NonNull String title, long id) {
        CategoriesFragment fragment = CategoriesFragment.newInstance(title, id);
        mFragmentManager.beginTransaction().replace(R.id.container, fragment, null).addToBackStack(null).commit();
    }
}
