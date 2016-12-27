package net.ginteam.carmen.network.api.service;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.network.api.ApiLinks;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Eugene on 12/27/16.
 */

public interface CityService {

    @GET(ApiLinks.CATALOG.CITIES)
    Observable <ResponseModel <List<CityModel>>> fetchCities();

}
