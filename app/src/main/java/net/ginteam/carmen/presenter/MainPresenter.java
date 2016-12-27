package net.ginteam.carmen.presenter;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import net.ginteam.carmen.contract.MainContract;
import net.ginteam.carmen.manager.ApiGoogleManager;

/**
 * Created by vadik on 27.12.16.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    private ApiGoogleManager mApiGoogleManager;

    public MainPresenter(MainContract.View view) {
        attachView(view);
    }

    @Override
    public void getLastLocation() {
        mApiGoogleManager.getLastLocation(new ApiGoogleManager.OnReceiveLocationListener() {
            @Override
            public void onLocationReceived(Location location) {
                Log.e("PIZDA", " sdad" + location);
            }

            @Override
            public void onLocationReceiveFailure() {

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
    public void attachView(MainContract.View view) {
        mView = view;
        mApiGoogleManager = ApiGoogleManager.getInstance(mView.getContext(), mView.getActivity());
    }

    @Override
    public void detachView() {

    }
}
