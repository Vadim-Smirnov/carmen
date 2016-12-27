package net.ginteam.carmen.view.activity;

import android.os.Bundle;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.view.fragment.category.CategoryListFragment;
import net.ginteam.carmen.view.fragment.city.CityListFragment;

public class MainActivity extends NavigationViewActivity implements CategoryListFragment.OnCategorySelectedListener,
        CityListFragment.OnCitySelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
    }

    @Override
    public void onCategorySelected(CategoryModel category) {
        Toast.makeText(this, category.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCitySelected(CityModel city) {
        Toast.makeText(this, city.getName(), Toast.LENGTH_SHORT).show();
    }

}
