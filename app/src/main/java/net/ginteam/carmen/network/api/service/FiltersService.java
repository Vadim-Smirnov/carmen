package net.ginteam.carmen.network.api.service;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.network.api.ApiLinks;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Eugene on 1/16/17.
 */

public interface FiltersService {

    @GET(ApiLinks.CATALOG.COMPANIES_BY_CATEGORY)
    Observable<ResponseModel <List <CompanyModel>>> calculateCount(
            @Path(ApiLinks.CATALOG.ID) int categoryId,
            @Query(ApiLinks.CATALOG.SEARCH) String filter,
            @Query(ApiLinks.CATALOG.LIMIT) int limit
    );

}
