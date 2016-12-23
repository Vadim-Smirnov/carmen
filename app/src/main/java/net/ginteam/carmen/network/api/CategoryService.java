package net.ginteam.carmen.network.api;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.category.CategoryModel;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Eugene on 12/23/16.
 */

public interface CategoryService {

    @GET(ApiLinks.CATALOG.CATEGORIES)
    Observable <ResponseModel<List<CategoryModel>>> fetchCategories();

}
