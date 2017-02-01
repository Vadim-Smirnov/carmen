package net.ginteam.carmen.network.api.service;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.model.company.MapCompanyModel;
import net.ginteam.carmen.network.api.ApiLinks;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Eugene on 12/27/16.
 */

public interface CompanyService {

    @GET(ApiLinks.CATALOG.COMPANIES_BY_CATEGORY)
    Observable<ResponseModel<List<CompanyModel>>> fetchCompanies(
            @Path(ApiLinks.CATALOG.ID) int categoryId,
            @Query(ApiLinks.CATALOG.LAT) String lat,
            @Query(ApiLinks.CATALOG.LNG) String lng,
            @Query(ApiLinks.CATALOG.SEARCH) String filter,
            @Query(ApiLinks.CATALOG.SORT_FIELD) String sortField,
            @Query(ApiLinks.CATALOG.SORT_TYPE) String sortType,
            @Query(ApiLinks.CATALOG.PAGE) int page
    );

    @GET(ApiLinks.CATALOG.COMPANIES_BY_BOUNDS)
    Observable<ResponseModel<List<MapCompanyModel>>> fetchCompanies(
            @Path(ApiLinks.CATALOG.ID) int categoryId,
            @Query(ApiLinks.CATALOG.SEARCH) String filter,
            @Query(ApiLinks.CATALOG.BOUNDS) String bounds
    );

    @GET(ApiLinks.CATALOG.COMPANY_BY_ID)
    Observable<ResponseModel<CompanyModel>> fetchCompanyDetail(
            @Path(ApiLinks.CATALOG.ID) int companyId,
            @Query(ApiLinks.CATALOG.LAT) String lat,
            @Query(ApiLinks.CATALOG.LNG) String lng,
            @Query(ApiLinks.CATALOG.WITH) String relations
    );

    @GET(ApiLinks.CATALOG.POPULAR_COMPANIES)
    Observable<ResponseModel<List<CompanyModel>>> fetchPopular(
            @Path(ApiLinks.CATALOG.CITY_ID) int cityId,
            @Query(ApiLinks.CATALOG.LAT) String lat,
            @Query(ApiLinks.CATALOG.LNG) String lng
    );

    @GET(ApiLinks.AUTH.GET_FAVORITES)
    Observable<ResponseModel<List<CompanyModel>>> fetchFavorites(
            @Query(ApiLinks.CATALOG.LAT) String lat,
            @Query(ApiLinks.CATALOG.LNG) String lng
    );

    @GET(ApiLinks.AUTH.GET_RECENTLY_WATCHED)
    Observable<ResponseModel<List<CompanyModel>>> fetchRecentlyWatched(
            @Query(ApiLinks.CATALOG.LAT) String lat,
            @Query(ApiLinks.CATALOG.LNG) String lng
    );

    @POST(ApiLinks.AUTH.FAVORITES_BY_ID)
    Observable<ResponseModel<String>> addToFavorites(@Path(ApiLinks.AUTH.ID) int id);

    @DELETE(ApiLinks.AUTH.FAVORITES_BY_ID)
    Observable<ResponseModel<String>> removeFromFavorites(@Path(ApiLinks.AUTH.ID) int id);


}
