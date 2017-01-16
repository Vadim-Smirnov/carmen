package net.ginteam.carmen.provider.filter;

import com.google.gson.Gson;

import net.ginteam.carmen.CarmenApplication;
import net.ginteam.carmen.R;
import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.model.filter.FilterModel;
import net.ginteam.carmen.network.api.service.FiltersService;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriberWithMeta;
import net.ginteam.carmen.provider.ModelCallback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eugene on 1/15/17.
 */

public class FiltersProvider {

    private static FiltersProvider sInstance;

    private Map<Integer, List<FilterModel>> mCachedFilters;

    private FiltersProvider() {
        mCachedFilters = new HashMap<>();
    }

    public static FiltersProvider getInstance() {
        if (sInstance == null) {
            sInstance = new FiltersProvider();
        }
        return sInstance;
    }

    public void fetchForCategory(int categoryId, ModelCallback<List<FilterModel>> completion) {
        if (mCachedFilters.containsKey(categoryId)) {
            completion.onSuccess(mCachedFilters.get(categoryId));
            return;
        }
        fetchFromResources(completion);
    }

    public void calculateCount(int categoryId, String filter, final ModelCallback <Pagination> completion) {
        FiltersService filtersService = ApiManager.getInstance().getService(FiltersService.class);
        filtersService
                .calculateCount(categoryId, filter, 0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriberWithMeta<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel, Pagination pagination) {
                        completion.onSuccess(pagination);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

    private void fetchFromResources(ModelCallback<List<FilterModel>> completion) {
        String json = CarmenApplication.getContext().getString(R.string.filters_example);
        FilterModel[] filterModels = new Gson().fromJson(json, FilterModel[].class);
        completion.onSuccess(Arrays.asList(filterModels));
    }

}
