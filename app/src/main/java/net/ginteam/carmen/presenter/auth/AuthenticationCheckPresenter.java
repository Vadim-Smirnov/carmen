package net.ginteam.carmen.presenter.auth;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;

import net.ginteam.carmen.contract.auth.AuthenticationCheckContract;
import net.ginteam.carmen.manager.ApiGoogleManager;
import net.ginteam.carmen.model.auth.UserModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.auth.AuthProvider;

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

                    @Override
                    public void onLocationReceiveFailure() {
                        mView.showCityListView();
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
        mApiGoogleManager = ApiGoogleManager.getInstance(mView.getActivity());
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
