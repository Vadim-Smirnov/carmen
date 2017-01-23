package net.ginteam.carmen.presenter.auth;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import net.ginteam.carmen.contract.auth.AuthenticationCheckContract;
import net.ginteam.carmen.manager.ApiGoogleManager;
import net.ginteam.carmen.manager.PreferencesManager;
import net.ginteam.carmen.model.auth.UserModel;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.auth.AuthProvider;
import net.ginteam.carmen.provider.city.CitiesProvider;

/**
 * Created by vadik on 27.12.16.
 */

public class AuthenticationCheckPresenter implements AuthenticationCheckContract.Presenter {

    private AuthenticationCheckContract.View mView;
    private ApiGoogleManager mApiGoogleManager;

    @Override
    public void checkAuthentication() {
        mApiGoogleManager
                .getLastLocation(new ApiGoogleManager.OnReceiveLocationListener() {
                    @Override
                    public void onLocationReceived(Location location) {
                        saveCity(location);
                        fetchCurrentUser();
                    }

                    @Override
                    public void onLocationReceiveFailure() {
                        if (PreferencesManager.getInstance().getCity() == null) {
                            mView.showCityListView();
                            return;
                        }
                        fetchCurrentUser();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mApiGoogleManager.onActivityResult(requestCode, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mApiGoogleManager.onRequestPermissionsResult(requestCode, permissions,grantResults);
    }

    @Override
    public void attachView(AuthenticationCheckContract.View view) {
        mView = view;
        mApiGoogleManager = new ApiGoogleManager(mView.getActivity());
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void fetchCurrentUser() {
        AuthProvider
                .getInstance()
                .fetchCurrentUser(new ModelCallback<UserModel>() {
                    @Override
                    public void onSuccess(UserModel resultModel) {
                        mView.showMainView();
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showAuthenticationView();
                    }
                });
    }

    private void saveCity(Location location) {
        CitiesProvider
                .getInstance()
                .fetchCityByPoint(location, new ModelCallback<CityModel>() {
                    @Override
                    public void onSuccess(CityModel resultModel) {
                        PreferencesManager
                                .getInstance()
                                .setCity(new Gson().toJson(resultModel));
                        Log.e("CityByPoint", "The city received: " +
                                PreferencesManager.getInstance().getCity());
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.e("CityByPoint", "Error city receiving: " + message);
                    }
                });
    }

}
