package net.ginteam.carmen.data.remote.api.request;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.model.company.MapCompanyModel;
import net.ginteam.carmen.network.api.ApiLinks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface CompanyApi {

    @GET(ApiLinks.CATALOG.COMPANIES_BY_CATEGORY)
    Call<ResponseModel<List<CompanyModel>>> fetchCompanies(
            @Path(ApiLinks.CATALOG.ID) int categoryId,
            @Query(ApiLinks.CATALOG.LAT) String lat,
            @Query(ApiLinks.CATALOG.LNG) String lng,
            @Query(ApiLinks.CATALOG.BOUND) String bound,
            @Query(ApiLinks.CATALOG.SEARCH) String filter,
            @Query(ApiLinks.CATALOG.SORT_FIELD) String sortField,
            @Query(ApiLinks.CATALOG.SORT_TYPE) String sortType,
            @Query(ApiLinks.CATALOG.PAGE) int page
    );

    @GET(ApiLinks.CATALOG.COMPANIES_BY_BOUNDS)
    Call<ResponseModel<List<MapCompanyModel>>> fetchCompanies(
            @Path(ApiLinks.CATALOG.ID) int categoryId,
            @Query(ApiLinks.CATALOG.SEARCH) String filter,
            @Query(ApiLinks.CATALOG.BOUNDS) String bounds
    );

    @GET(ApiLinks.CATALOG.COMPANY_BY_ID)
    Call<ResponseModel<CompanyModel>> fetchCompanyDetail(
            @Path(ApiLinks.CATALOG.ID) int companyId,
            @Query(ApiLinks.CATALOG.LAT) String lat,
            @Query(ApiLinks.CATALOG.LNG) String lng,
            @Query(ApiLinks.CATALOG.WITH) String relations
    );

    @GET(ApiLinks.CATALOG.POPULAR_COMPANIES)
    Call<ResponseModel<List<CompanyModel>>> fetchPopular(
            @Path(ApiLinks.CATALOG.CITY_ID) int cityId,
            @Query(ApiLinks.CATALOG.LAT) String lat,
            @Query(ApiLinks.CATALOG.LNG) String lng
    );

    @GET(ApiLinks.AUTH.GET_RECENTLY_WATCHED)
    Call<ResponseModel<List<CompanyModel>>> fetchRecentlyWatched(
            @Query(ApiLinks.CATALOG.LAT) String lat,
            @Query(ApiLinks.CATALOG.LNG) String lng
    );

}
