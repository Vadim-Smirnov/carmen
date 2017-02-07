package net.ginteam.carmen.data.provider.category;

import net.ginteam.carmen.data.provider.DataSourceProvider;
import net.ginteam.carmen.data.remote.api.task.Callback;
import net.ginteam.carmen.model.category.CategoryModel;

import java.util.List;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface CategoriesSourceProvider extends DataSourceProvider {

    void fetchCategories(final CategoriesListCallback callback);

    abstract class CategoriesListCallback extends Callback <List <CategoryModel>> {}

}