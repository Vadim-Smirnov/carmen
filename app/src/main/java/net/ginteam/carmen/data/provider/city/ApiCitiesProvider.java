package net.ginteam.carmen.data.provider.city;

import net.ginteam.carmen.data.remote.api.request.CityApi;
import net.ginteam.carmen.manager.ApiManager;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public class ApiCitiesProvider implements CitiesSourceProvider {

    private CityApi mCityApi = ApiManager.getInstance().getService(CityApi.class);

    @Override
    public void fetchCities(CitiesListCallback callback) {
        mCityApi.fetchCities().enqueue(callback);
    }

    @Override
    public void fetchCityByPoint(Double lat, Double lon, CityCallback callback) {
        mCityApi.fetchCityByPoint(lat, lon).enqueue(callback);
    }

}
