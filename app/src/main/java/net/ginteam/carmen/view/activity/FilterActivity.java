package net.ginteam.carmen.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.ginteam.carmen.R;
import net.ginteam.carmen.view.custom.FilterEditText;

public class FilterActivity extends ToolbarActivity implements FilterEditText.OnFilterChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        updateDependencies();
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
            case R.id.menu_item_clear_filters:
                clearFilters();
                break;
        }
        return true;
    }

    @Override
    public void onFilterChanged(FilterEditText filterEditText) {

    }

    private void updateDependencies() {
        setTitle(getString(R.string.filter_activity_title));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void clearFilters() {}

}