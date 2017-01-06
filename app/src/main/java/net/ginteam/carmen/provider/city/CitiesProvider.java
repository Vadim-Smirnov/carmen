package net.ginteam.carmen.provider.city;

import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.network.api.service.CityService;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriber;
import net.ginteam.carmen.provider.ModelCallback;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eugene on 12/27/16.
 */

public class CitiesProvider {

    private static CitiesProvider sInstance;

    private List <CityModel> mCachedCities;

    private CitiesProvider() {}

    public static CitiesProvider getInstance() {
        if (sInstance == null) {
            sInstance = new CitiesProvider();
        }
        return sInstance;
    }

    public void fetchCities(ModelCallback<List<CityModel>> completion) {
        if (mCachedCities != null) {
            completion.onSuccess(mCachedCities);
            return;
        }
        fetchFromServer(completion);
    }

    private void fetchFromServer(final ModelCallback<List<CityModel>> completion) {
        CityService cityService = ApiManager.getInstance().getService(CityService.class);
        cityService
                .fetchCities()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<List<CityModel>>() {
                    @Override
                    public void onSuccess(List<CityModel> resultModel) {
                        mCachedCities = resultModel;
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

}
