package net.ginteam.carmen.data.remote.api.request;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.network.api.ApiLinks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface CategoryApi {

    @GET(ApiLinks.CATALOG.GET_CATEGORIES)
    Call<ResponseModel<List<CategoryModel>>> fetchCategories();

}