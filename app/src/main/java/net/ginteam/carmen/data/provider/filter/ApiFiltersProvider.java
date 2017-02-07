package net.ginteam.carmen.data.provider.filter;

import com.google.gson.Gson;

import net.ginteam.carmen.CarmenApplication;
import net.ginteam.carmen.R;
import net.ginteam.carmen.data.remote.api.request.FilterApi;
import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.model.filter.FilterModel;

import java.util.Arrays;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public class ApiFiltersProvider implements FiltersSourceProvider {

    @Override
    public void fetchFiltersForCategory(int categoryId, FiltersCallback callback) {
        String json = CarmenApplication.getContext().getString(R.string.filters_example);
        FilterModel[] filterModels = new Gson().fromJson(json, FilterModel[].class);
        callback.onSuccess(Arrays.asList(filterModels));
    }

    @Override
    public void getCompaniesCountWithFilter(int categoryId, String filter, FilterableCountCallback callback) {
        ApiManager
                .getInstance()
                .getService(FilterApi.class)
                .calculateCount(categoryId, filter)
                .enqueue(callback);
    }
}
