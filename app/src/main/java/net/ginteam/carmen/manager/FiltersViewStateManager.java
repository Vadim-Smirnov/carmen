package net.ginteam.carmen.manager;

import android.content.Context;
import android.content.SharedPreferences;

import net.ginteam.carmen.CarmenApplication;
import net.ginteam.carmen.view.custom.FilterEditText;

import java.util.List;

/**
 * Created by Eugene on 1/17/17.
 */

public class FiltersViewStateManager {

    private final String PREFERENCES_NAME = "filters_view_state";
    private final String CATEGORY_ID = "category_id";
    private final String QUERY = "filter_query";
    private final String FIELD_QUERY = "_query";
    private final String RESULTS_COUNT = "results_count";

    private static FiltersViewStateManager sInstance;

    private SharedPreferences mPreferences;

    private FiltersViewStateManager(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static FiltersViewStateManager getInstance() {
        if (sInstance == null) {
            sInstance = new FiltersViewStateManager(CarmenApplication.getContext());
        }
        return sInstance;
    }

    public void saveFiltersState(int categoryId, List<FilterEditText> fields, String filterQuery) {
        SharedPreferences.Editor editor = mPreferences.edit();

        editor.putInt(CATEGORY_ID, categoryId);
        editor.putString(QUERY, filterQuery);

        for (FilterEditText currentField : fields) {
            String key = currentField.getHint();
            String value = currentField.getText();

            editor.putString(key + FIELD_QUERY, currentField.getFilterQuery());
            editor.putString(key, value);
        }
        editor.apply();
    }

    public void restoreFiltersState(int categoryId, List<FilterEditText> fields) {
        int savedCategoryId = mPreferences.getInt(CATEGORY_ID, 0);
        if (savedCategoryId != categoryId) {
            resetFiltersState();
        }

        for (FilterEditText currentField : fields) {
            String key = currentField.getHint();
            String value = mPreferences.getString(key, "");

            currentField.setFilterQuery(mPreferences.getString(key + FIELD_QUERY, ""));
            currentField.setText(value);
        }
    }

    public String restoreFiltersQuery() {
        return mPreferences.getString(QUERY, "");
    }

    public void resetFiltersState() {
        mPreferences.edit().clear().apply();
    }

}
