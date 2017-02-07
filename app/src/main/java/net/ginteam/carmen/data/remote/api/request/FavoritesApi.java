package net.ginteam.carmen.data.remote.api.request;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.network.api.ApiLinks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface FavoritesApi {

    @GET(ApiLinks.AUTH.GET_FAVORITES)
    Call<ResponseModel<List<CompanyModel>>> fetchFavorites(
            @Query(ApiLinks.CATALOG.LAT) String lat,
            @Query(ApiLinks.CATALOG.LNG) String lng
    );

    @POST(ApiLinks.AUTH.FAVORITES_BY_ID)
    Call<ResponseModel<String>> addToFavorites(@Path(ApiLinks.AUTH.ID) int id);

    @DELETE(ApiLinks.AUTH.FAVORITES_BY_ID)
    Call<ResponseModel<String>> removeFromFavorites(@Path(ApiLinks.AUTH.ID) int id);

}
