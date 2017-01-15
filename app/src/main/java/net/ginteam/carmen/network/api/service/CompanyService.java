package net.ginteam.carmen.network.api.service;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.auth.AuthModel;
import net.ginteam.carmen.model.company.CompanyModel;
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
    Observable <ResponseModel <List <CompanyModel>>> fetchCompanies(@Path(ApiLinks.CATALOG.ID) int categoryId);

    @GET(ApiLinks.AUTH.GET_FAVORITES)
    Observable <ResponseModel <List <CompanyModel>>> fetchFavorites();

    @GET(ApiLinks.AUTH.GET_LAST_VIEWED)
    Observable <ResponseModel <List <CompanyModel>>> fetchRecentlyWatched();

    @POST(ApiLinks.AUTH.FAVORITES_BY_ID)
    Observable <ResponseModel<String>> addToFavorites(@Path(ApiLinks.AUTH.ID) int id);

    @DELETE(ApiLinks.AUTH.FAVORITES_BY_ID)
    Observable <ResponseModel<String>> removeFromFavorites(@Path(ApiLinks.AUTH.ID) int id);



}
