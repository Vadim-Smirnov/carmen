package net.ginteam.carmen.data.remote.api.request;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.network.api.ApiLinks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface FilterApi {

    @GET(ApiLinks.CATALOG.COMPANIES_BY_CATEGORY)
    Call<ResponseModel<List<CompanyModel>>> calculateCount(
            @Path(ApiLinks.CATALOG.ID) int categoryId,
            @Query(ApiLinks.CATALOG.SEARCH) String filter
    );

}
