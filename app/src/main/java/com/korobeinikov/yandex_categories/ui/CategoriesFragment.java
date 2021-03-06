package com.korobeinikov.yandex_categories.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.korobeinikov.yandex_categories.R;

import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.CONTENT_URI;
import static com.korobeinikov.yandex_categories.model.CategoriesContract.Categories.PARENT_ID;
import static com.korobeinikov.yandex_categories.model.Category.ID_ROOT_CATEGORY;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class CategoriesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_CATEGORY_TITLE = "ARG_CATEGORY_TITLE";
    private static final String ARG_PARENT_CATEGORY_ID = "ARG_PARENT_CATEGORY_ID";

    private ListView mCategoriesList;
    private ImageView mEmptyIcon;
    private TextView mEmptyLabel;
    private CategoriesCursorAdapter mAdapter;
    private OnCategoryClickListener mOnCategoryClickListener;

    private String mTitle;
    private long mParentCategoryID;

    public static CategoriesFragment newInstance(@NonNull String title, long parentID) {
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY_TITLE, title);
        args.putLong(ARG_PARENT_CATEGORY_ID, parentID);
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnCategoryClickListener = (OnCategoryClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActionBar().setTitle(mTitle);
        getActionBar().setDisplayHomeAsUpEnabled(!isRootCategory());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        setupAdapter();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnCategoryClickListener = null;
    }

    private void initViews(View view) {
        mEmptyIcon = (ImageView) view.findViewById(R.id.ivEmptyIcon);
        mEmptyLabel = (TextView) view.findViewById(R.id.tvEmptyCategoryLabel);
        mCategoriesList = (ListView) view.findViewById(R.id.categoriesList);
        mCategoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnCategoryClickListener.onCategoryClick(mAdapter.getCategoryTitle(position), id);
            }
        });
    }

    private void parseArguments() {
        Bundle args = getArguments();
        mTitle = args.getString(ARG_CATEGORY_TITLE);
        mParentCategoryID = args.getLong(ARG_PARENT_CATEGORY_ID);
    }

    private void setupAdapter() {
        mAdapter = new CategoriesCursorAdapter(getContext(), null, 0);
        mCategoriesList.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    private void showEmptyIndicator(boolean isShown) {
        int mask = isShown ? View.VISIBLE : View.GONE;
        mEmptyIcon.setVisibility(mask);
        mEmptyLabel.setVisibility(mask);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String whereClause = isRootCategory() ? PARENT_ID + " IS NULL" : PARENT_ID + " = " + mParentCategoryID;
        return new CursorLoader(getContext(), CONTENT_URI, null, whereClause, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        showEmptyIndicator(!isRootCategory() && data.getCount() == 0);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    private boolean isRootCategory() {
        return mParentCategoryID == ID_ROOT_CATEGORY;
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(@NonNull String title, long id);
    }
}
