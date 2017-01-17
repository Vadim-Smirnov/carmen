package net.ginteam.carmen.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.filter.FilterContract;
import net.ginteam.carmen.model.filter.FilterModel;
import net.ginteam.carmen.presenter.filter.FiltersPresenter;
import net.ginteam.carmen.view.custom.FilterEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilterActivity extends ToolbarActivity implements FilterContract.View, FilterEditText.OnFilterChangeListener, View.OnClickListener {

    public static final int REQUEST_CODE = 2017;
    public static final String CATEGORY_ID_ARGUMENT = "category_id";
    public static final String RESULT_FILTER_ARGUMENT = "result_filter";

    private FilterContract.Presenter mPresenter;

    private int mCategoryId;
    private String mResultFilterQuery;

    private LinearLayout mLayoutFilterFields;
    private TextView mTextViewFiltersResultCount;
    private ProgressBar mProgressBarFilters;

    private List <FilterEditText> mFilterFieldsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        updateDependencies();

        mPresenter = new FiltersPresenter();
        mPresenter.attachView(this);
        mPresenter.fetchFiltersForCategory(mCategoryId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filters_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_item_reset_filters:
                resetFilters();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        mPresenter.saveViewState(mCategoryId, mFilterFieldsList, mResultFilterQuery);

        Intent intent = new Intent();
        intent.putExtra(RESULT_FILTER_ARGUMENT, mResultFilterQuery);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onFilterChanged(FilterEditText filterEditText) {
        mResultFilterQuery = getResultFilterQuery();
        mPresenter.updateResultsWithFilter(mCategoryId, mResultFilterQuery);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading(boolean isLoading) {
        mProgressBarFilters.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mTextViewFiltersResultCount.setVisibility(!isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {
        mTextViewFiltersResultCount.setText(message);
    }

    @Override
    public void showResultsCount(int count) {
        mTextViewFiltersResultCount.setText(
                String.format(Locale.getDefault(), getString(R.string.results_count_string), count)
        );
    }

    @Override
    public void showFilters(List<FilterModel> filters) {
        mFilterFieldsList = new ArrayList<>();
        for (FilterModel currentFilter : filters) {
            FilterEditText filterField = new FilterEditText(getContext());
            filterField.setFilterModel(currentFilter);
            mFilterFieldsList.add(filterField);
        }
        updateFiltersDependencies();
    }

    private void updateDependencies() {
        mCategoryId = getIntent().getIntExtra(CATEGORY_ID_ARGUMENT, 0);

        setTitle(getString(R.string.filter_activity_title));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLayoutFilterFields = (LinearLayout) findViewById(R.id.layout_filters);
        mTextViewFiltersResultCount = (TextView) findViewById(R.id.text_view_filters_result_count);
        mProgressBarFilters = (ProgressBar) findViewById(R.id.progress_bar_filter);

        findViewById(R.id.text_view_show_results).setOnClickListener(this);
    }

    private void updateFiltersDependencies() {
        for (FilterEditText currentFilter : mFilterFieldsList) {
            currentFilter.setOnFilterChangeListener(this);
            mLayoutFilterFields.addView(currentFilter);
        }
        mResultFilterQuery = mPresenter.restoreViewState(mCategoryId, mFilterFieldsList);
    }

    private String getResultFilterQuery() {
        String resultFilter = "";
        for (FilterEditText currentFilter : mFilterFieldsList) {
            resultFilter += currentFilter.getFilterQuery();
        }
        return resultFilter.isEmpty() ? "" : resultFilter.substring(0, resultFilter.length() - 1);
    }

    private void resetFilters() {
        for (FilterEditText currentFilter : mFilterFieldsList) {
            currentFilter.resetFilter();
        }
    }

}