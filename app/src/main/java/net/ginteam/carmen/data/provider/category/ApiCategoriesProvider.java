package net.ginteam.carmen.data.provider.category;

import net.ginteam.carmen.data.remote.api.request.CategoryApi;
import net.ginteam.carmen.manager.ApiManager;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public class ApiCategoriesProvider implements CategoriesSourceProvider {

    @Override
    public void fetchCategories(final CategoriesListCallback callback) {
        ApiManager
                .getInstance()
                .getService(CategoryApi.class)
                .fetchCategories()
                .enqueue(callback);
    }

}
