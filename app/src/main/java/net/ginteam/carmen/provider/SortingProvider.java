package net.ginteam.carmen.provider;

import com.google.gson.Gson;

import net.ginteam.carmen.CarmenApplication;
import net.ginteam.carmen.R;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.SortingModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vadik on 18.01.17.
 */

public class SortingProvider {

    private static SortingProvider sInstance;

    private Map<Integer, List<SortingModel>> mCachedSorting;

    private SortingProvider() {
        mCachedSorting = new HashMap<>();
    }

    public static SortingProvider getInstance() {
        if (sInstance == null) {
            sInstance = new SortingProvider();
        }
        return sInstance;
    }

    public void fetchForCategory(int categoryId, ModelCallback<List<SortingModel>> completion) {
        if (mCachedSorting.containsKey(categoryId)) {
            completion.onSuccess(mCachedSorting.get(categoryId));
            return;
        }
        fetchFromResources(completion);
    }

    private void fetchFromResources(ModelCallback<List<SortingModel>> completion) {
        String json = CarmenApplication.getContext().getString(R.string.sorting_example);
        SortingModel[] sortingModels = new Gson().fromJson(json, SortingModel[].class);
        completion.onSuccess(Arrays.asList(sortingModels));
    }
}
