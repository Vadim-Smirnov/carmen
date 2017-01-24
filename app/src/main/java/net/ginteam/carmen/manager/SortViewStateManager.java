package net.ginteam.carmen.manager;

import android.content.Context;
import android.content.SharedPreferences;

import net.ginteam.carmen.CarmenApplication;

/**
 * Created by Eugene on 1/24/17.
 */

public class SortViewStateManager {

    private final String PREFERENCES_NAME = "sort_view_state";
    private final String CATEGORY_ID = "category_id";
    private final String CHECKED_VIEW_ID = "checked_view_id";
    private final String SORT_FIELD = "sort_field";
    private final String SORT_TYPE = "sort_type";

    private static SortViewStateManager sInstance;

    private SharedPreferences mPreferences;

    private SortViewStateManager(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static SortViewStateManager getInstance() {
        if (sInstance == null) {
            sInstance = new SortViewStateManager(CarmenApplication.getContext());
        }
        return sInstance;
    }

    public void saveViewState(SortViewState sortViewState) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(CATEGORY_ID, sortViewState.getCategoryId());
        editor.putInt(CHECKED_VIEW_ID, sortViewState.getCheckedViewIndex());
        editor.putString(SORT_FIELD, sortViewState.getSortField());
        editor.putString(SORT_TYPE, sortViewState.getSortType());
        editor.apply();
    }

    public SortViewState restoreViewState(int categoryId) {
        int savedCategoryId = mPreferences.getInt(CATEGORY_ID, 0);
        if (savedCategoryId != categoryId) {
            resetFiltersState();
            return null;
        }

        int checkedViewId = mPreferences.getInt(CHECKED_VIEW_ID, 0);
        String sortField = mPreferences.getString(SORT_FIELD, "");
        String sortType = mPreferences.getString(SORT_TYPE, "");

        return new SortViewState(savedCategoryId, checkedViewId, sortField, sortType);
    }

    public void resetFiltersState() {
        mPreferences.edit().clear().apply();
    }

    public static class SortViewState {

        private int mCategoryId;
        private int mCheckedViewIndex;
        private String mSortField;
        private String mSortType;

        public SortViewState(int categoryId, int checkedViewIndex, String sortField, String sortType) {
            mCategoryId = categoryId;
            mCheckedViewIndex = checkedViewIndex;
            mSortField = sortField;
            mSortType = sortType;
        }

        public int getCategoryId() {
            return mCategoryId;
        }

        public void setCategoryId(int categoryId) {
            mCategoryId = categoryId;
        }

        public int getCheckedViewIndex() {
            return mCheckedViewIndex;
        }

        public void setCheckedViewIndex(int checkedViewIndex) {
            mCheckedViewIndex = checkedViewIndex;
        }

        public String getSortField() {
            return mSortField;
        }

        public void setSortField(String sortField) {
            mSortField = sortField;
        }

        public String getSortType() {
            return mSortType;
        }

        public void setSortType(String sortType) {
            mSortType = sortType;
        }

    }

}
