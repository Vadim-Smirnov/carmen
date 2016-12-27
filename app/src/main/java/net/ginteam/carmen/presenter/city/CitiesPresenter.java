package net.ginteam.carmen.presenter.city;

import net.ginteam.carmen.contract.city.CityContract;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.city.CitiesProvider;

import java.util.List;

/**
 * Created by Eugene on 12/27/16.
 */

public class CitiesPresenter implements CityContract.Presenter {

    private CityContract.View mView;

    @Override
    public void selectCity(CityModel city) {

    }

    @Override
    public void fetchData() {
        mView.showLoading(true);

        CitiesProvider
                .getInstance()
                .fetch(new ModelCallback<List<CityModel>>() {
                    @Override
                    public void onSuccess(List<CityModel> resultModel) {
                        mView.showLoading(false);
                        mView.showCities(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showError(message);
                    }
                });
    }

    @Override
    public void attachView(CityContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
