package net.ginteam.carmen.presenter;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import net.ginteam.carmen.contract.MapContract;
import net.ginteam.carmen.manager.ApiGoogleManager;
import net.ginteam.carmen.manager.PreferencesManager;
import net.ginteam.carmen.model.Point;

/**
 * Created by vadik on 10.01.17.
 */

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mView;
    private ApiGoogleManager mApiGoogleManager;

    private Location mLastUserLocation;

    public MapPresenter() {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mApiGoogleManager.onActivityResult(requestCode, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mApiGoogleManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void getLastUserLocation() {
        mApiGoogleManager
                .getLastLocation(new ApiGoogleManager.OnReceiveLocationListener() {
                    @Override
                    public void onLocationReceived(Location location) {
                        mLastUserLocation = location;
                        mView.showGoogleMap(new LatLng(mLastUserLocation.getLatitude(),
                                mLastUserLocation.getLongitude()));
                    }

                    @Override
                    public void onLocationReceiveFailure() {
                        Point cityPoint = PreferencesManager.getInstance().getCity().getPoint();
                        mView.showGoogleMap(new LatLng(cityPoint.getLatitude(), cityPoint.getLongitude()));
                    }
                });
    }

    @Override
    public void fetchCompaniesByBounds(LatLngBounds bounds) {

    }

    @Override
    public void attachView(MapContract.View view) {
        mView = view;
        mApiGoogleManager = new ApiGoogleManager(mView.getActivity());
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
