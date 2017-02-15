package net.ginteam.carmen.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.provider.auth.AuthProvider;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.view.activity.company.CompanyDetailActivity;
import net.ginteam.carmen.view.activity.filter.FilterActivity;
import net.ginteam.carmen.view.activity.map.MapActivity;
import net.ginteam.carmen.view.fragment.SortingFragment;
import net.ginteam.carmen.view.fragment.category.CategoryListFragment;
import net.ginteam.carmen.view.fragment.city.CityListFragment;
import net.ginteam.carmen.view.fragment.company.CompanyListFragment;

public class MainActivity extends NavigationViewActivity implements
        CategoryListFragment.OnCategorySelectedListener, CityListFragment.OnCitySelectedListener,
        CompanyListFragment.OnCompanySelectedListener, CompanyListFragment.OnSelectedItemsListener,
        SortingFragment.OnSortTypeSelectedListener {

    private CategoryModel mSelectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        Toast.makeText(this, R.string.test, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        onNavigationItemSelected(mNavigationView.getMenu().getItem(0));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FilterActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            String searchFilter = data.getStringExtra(FilterActivity.RESULT_FILTER_ARGUMENT);
            if (CompanyListFragment.class.equals(mCurrentFragment.getClass())) {
                ((CompanyListFragment) mCurrentFragment).setSearchFilter(searchFilter);
            }
            Log.d("FilterActivity", searchFilter);
        }
    }

    @Override
    public void onShowMap(int categoryId) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra(MapActivity.CATEGORY_ID_ARG, categoryId);
        startActivity(intent);
    }

    @Override
    public void onShowCategoriesDialog() {
        DialogFragment newFragment = CategoryListFragment.newInstance(true);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onShowFiltersActivity() {
        startFilterActivityForResult(mSelectedCategory);
    }

    @Override
    public void onShowSortDialog() {
        SortingFragment
                .newInstance(mSelectedCategory.getId())
                .show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onSortSelected(String sortField, String sortType) {
        if (CompanyListFragment.class.equals(mCurrentFragment.getClass())) {
            ((CompanyListFragment) mCurrentFragment).setSortQuery(sortField, sortType);
        }
    }

    @Override
    public void onCategorySelected(CategoryModel category, boolean isDialog) {
        Toast.makeText(this, category.getName(), Toast.LENGTH_SHORT).show();

        prepareFragment(CompanyListFragment.newInstance(
                CompanyListFragment.COMPANY_LIST_TYPE.VERTICAL,
                CompanyListFragment.FETCH_COMPANY_TYPE.FOR_CATEGORY,
                category),
                false
        );

        mSelectedCategory = category;
    }

    @Override
    public void onCitySelected(CityModel city) {
        Toast.makeText(this, city.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompanySelected(CompanyModel company) {
        Toast.makeText(this, company.getName(), Toast.LENGTH_SHORT).show();

        Bundle arguments = new Bundle();
        arguments.putInt(CompanyDetailActivity.COMPANY_ARGUMENT, company.getId());
        ActivityUtils.showActivity(CompanyDetailActivity.class, arguments, false);
    }

    private void startFilterActivityForResult(CategoryModel categoryModel) {
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra(FilterActivity.CATEGORY_ID_ARGUMENT, categoryModel.getId());
        startActivityForResult(intent, FilterActivity.REQUEST_CODE);
    }

}
