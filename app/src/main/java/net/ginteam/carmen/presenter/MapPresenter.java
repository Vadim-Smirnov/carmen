package net.ginteam.carmen.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import net.ginteam.carmen.contract.MapContract;
import net.ginteam.carmen.manager.ApiGoogleManager;

/**
 * Created by vadik on 10.01.17.
 */

public class MapPresenter implements MapContract.Presenter, OnMapReadyCallback {

    private MapContract.View mView;

    private GoogleMap mGoogleMap;
    private Location mLastUserLocation;

    public MapPresenter(MapContract.View view) {
        attachView(view);
    }

    @Override
    public void prepareGoogleMap() {
        mView.getMapView().getMapAsync(this);
    }

    @Override
    public void animateToLocation(LatLng location) {
        if (location != null) {
            mGoogleMap.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(location)
                                    .zoom(20)
                                    .build()));
        }
    }

    @Override
    public void attachView(MapContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        ApiGoogleManager
                .getInstance(mView.getActivity())
                .getLastLocation(new ApiGoogleManager.OnReceiveLocationListener() {
                    @Override
                    public void onLocationReceived(Location location) {
                        mLastUserLocation = location;
                        if (checkPermission()) {
                            mGoogleMap.setMyLocationEnabled(true);
                        }
                        mView.showGoogleMap(mLastUserLocation);
                    }

                    @Override
                    public void onLocationReceiveFailure() {
                        mView.showError("Show cities list");

                        //TODO: show cities list
                        mView.showGoogleMap(mLastUserLocation);
                    }
                });

        UiSettings googleMapUiSettings = mGoogleMap.getUiSettings();
        googleMapUiSettings.setCompassEnabled(true);
        googleMapUiSettings.setAllGesturesEnabled(true);
        googleMapUiSettings.setZoomControlsEnabled(true);
    }

    private boolean checkPermission() {
        Context context = mView.getContext();
        return !(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }
}
