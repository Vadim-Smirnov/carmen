package net.ginteam.carmen.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import net.ginteam.carmen.contract.MapContract;
import net.ginteam.carmen.manager.ApiGoogleManager;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.utils.CompanyClusterRenderer;

/**
 * Created by vadik on 10.01.17.
 */

public class MapPresenter implements MapContract.Presenter, OnMapReadyCallback {

    private MapContract.View mView;
    private ApiGoogleManager mApiGoogleManager;

    private ClusterManager<CompanyModel> mClusterManager;
    private GoogleMap mGoogleMap;
    private Location mLastUserLocation;

    public MapPresenter() {}

    @Override
    public void animateToLocation(LatLng location) {
        if (location != null) {
            mGoogleMap.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(location)
                                    .zoom(12)
                                    .build()));
            fetchMarker();
        }
    }

    @Override
    public void attachView(MapContract.View view) {
        mView = view;
        mApiGoogleManager = ApiGoogleManager.getInstance(mView.getActivity());
        mView.getMapView().getMapAsync(this);
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
                        mView.showCityListView();
                        mView.showGoogleMap(mLastUserLocation);
                    }
                });

        mClusterManager = new ClusterManager<>(mView.getContext(), mGoogleMap);
        mClusterManager.setRenderer(new CompanyClusterRenderer(mView.getContext(), mGoogleMap, mClusterManager));
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<CompanyModel>() {
            @Override
            public boolean onClusterItemClick(CompanyModel companyModel) {
                Log.d("Marker", "CLICK");
                return true;
            }
        });
        mGoogleMap.setOnCameraIdleListener(mClusterManager);
        mGoogleMap.setOnMarkerClickListener(mClusterManager);

        UiSettings googleMapUiSettings = mGoogleMap.getUiSettings();
        googleMapUiSettings.setCompassEnabled(true);
        googleMapUiSettings.setAllGesturesEnabled(true);
        googleMapUiSettings.setMyLocationButtonEnabled(false);
    }

    private boolean checkPermission() {
        Context context = mView.getContext();
        return !(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mApiGoogleManager.onActivityResult(requestCode, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mApiGoogleManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void fetchMarker() {
        for (int i = 0; i < 10; i++) {
            mClusterManager.addItem(new CompanyModel());
        }
    }

}
