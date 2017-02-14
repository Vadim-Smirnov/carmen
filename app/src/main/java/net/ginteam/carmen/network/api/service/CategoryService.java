package net.ginteam.carmen.network.api.service;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.network.api.ApiLinks;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Eugene on 12/23/16.
 */

public interface CategoryService {

    @GET(ApiLinks.CATALOG.GET_CATEGORIES)
    Observable<ResponseModel<List<CategoryModel>>> fetchCategories(
            @Query(ApiLinks.CATALOG.CITY_ID) int cityId
    );

}
