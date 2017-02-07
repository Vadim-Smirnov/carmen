package net.ginteam.carmen.data.provider.city;

import net.ginteam.carmen.data.remote.api.task.Callback;
import net.ginteam.carmen.model.city.CityModel;

import java.util.List;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface CitiesSourceProvider {

    void fetchCities(final CitiesListCallback callback);

    void fetchCityByPoint(Double lat, Double lon, final CityCallback callback);

    abstract class CityCallback extends Callback <CityModel> {}

    abstract class CitiesListCallback extends Callback <List<CityModel>> {}

}
