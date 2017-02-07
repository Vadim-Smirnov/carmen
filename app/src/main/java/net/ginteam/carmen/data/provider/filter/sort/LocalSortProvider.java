package net.ginteam.carmen.data.provider.filter.sort;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import net.ginteam.carmen.CarmenApplication;
import net.ginteam.carmen.R;
import net.ginteam.carmen.model.SortingModel;

import java.io.StringReader;
import java.util.Arrays;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public class LocalSortProvider implements SortSourceProvider {

    @Override
    public void fetchSortForCategory(int categoryId, SortCallback callback) {
        String json = CarmenApplication.getContext().getString(R.string.sorting_example);
        JsonReader jr = new JsonReader(new StringReader(json));
        jr.setLenient(true);
        SortingModel[] sortingModels = new Gson().fromJson(jr, SortingModel[].class);
        callback.onSuccess(Arrays.asList(sortingModels));
    }

}
