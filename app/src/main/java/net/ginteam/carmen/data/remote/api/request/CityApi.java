package net.ginteam.carmen.data.remote.api.request;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.network.api.ApiLinks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface CityApi {

    @GET(ApiLinks.CATALOG.CITIES)
    Call<ResponseModel<List<CityModel>>> fetchCities();

    @GET(ApiLinks.CATALOG.CITIES_BY_POINT)
    Call<ResponseModel<CityModel>> fetchCityByPoint(
            @Query(ApiLinks.CATALOG.LAT) Double lat,
            @Query(ApiLinks.CATALOG.LNG) Double lon
    );

}
