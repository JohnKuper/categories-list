package com.korobeinikov.yandex_categories.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.korobeinikov.yandex_categories.R;
import com.korobeinikov.yandex_categories.db.CategoriesPersister;
import com.korobeinikov.yandex_categories.model.Category;
import com.korobeinikov.yandex_categories.network.CategoriesRequester;
import com.korobeinikov.yandex_categories.network.ResponseListener;
import com.korobeinikov.yandex_categories.ui.CategoriesFragment.OnCategoryClickListener;

import java.util.List;

import static com.korobeinikov.yandex_categories.ui.CategoriesFragment.ID_ROOT_CATEGORY;

public class MainActivity extends AppCompatActivity implements OnCategoryClickListener, ResponseListener<List<Category>> {

    private FragmentManager mFragmentManager;
    private CategoriesPersister mCategoriesPersister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCategoriesPersister = new CategoriesPersister(this);
        addInitialFragment();
        requestCategories();
    }

    private void addInitialFragment() {
        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = CategoriesFragment.newInstance(getString(R.string.root_categories_title), ID_ROOT_CATEGORY);
            mFragmentManager.beginTransaction().add(R.id.container, fragment, null).commit();
        }
    }

    private void requestCategories() {
        if (mCategoriesPersister.isDatabaseEmpty()) {
            CategoriesRequester requester = new CategoriesRequester(this);
            requester.executeRequest();
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
        mFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSuccess(List<Category> categories) {
        mCategoriesPersister.putToCache(categories);
    }

    @Override
    public void onFailure(final Throwable e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
