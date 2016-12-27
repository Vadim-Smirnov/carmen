package net.ginteam.carmen.contract.city;

import net.ginteam.carmen.contract.FetchContract;
import net.ginteam.carmen.model.city.CityModel;

import java.util.List;

/**
 * Created by Eugene on 12/27/16.
 */

public interface CityContract {

    interface View extends FetchContract.View {

        void showCities(List <CityModel> cities);

    }

    interface Presenter extends FetchContract.Presenter <View> {

        void selectCity(CityModel city);

    }

}
