package net.ginteam.carmen.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.MapContract;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.presenter.MapPresenter;
import net.ginteam.carmen.utils.CompanyClusterRenderer;

public class MapActivity extends ToolbarActivity implements MapContract.View, OnMapReadyCallback {

    public static final String CATEGORY_ID_ARG = "category_id";

    private MapContract.Presenter mPresenter;

    private ClusterManager<CompanyModel> mClusterManager;
    private GoogleMap mGoogleMap;

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        updateDependencies();

        mMapView.onCreate(savedInstanceState);

        mPresenter = new MapPresenter();
        mPresenter.attachView(this);

        mMapView.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showGoogleMap(final LatLng userLocation) {
        mMapView.onResume();
        if (userLocation != null) {
            if (checkPermission()) {
                mGoogleMap.setMyLocationEnabled(true);
            }
            mMapView.post(new Runnable() {
                @Override
                public void run() {
                    animateToLocation(userLocation);
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mPresenter.getLastUserLocation();

        mClusterManager = new ClusterManager<>(getContext(), mGoogleMap);
        mClusterManager.setRenderer(new CompanyClusterRenderer(getContext(), mGoogleMap, mClusterManager));
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
        googleMapUiSettings.setCompassEnabled(false);
        googleMapUiSettings.setAllGesturesEnabled(true);
        googleMapUiSettings.setMyLocationButtonEnabled(false);
    }

    @Override
    public MapView getMapView() {
        return mMapView;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void showCityListView() {}

    @Override
    public void animateToLocation(LatLng location) {
        if (location != null) {
            mGoogleMap.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(location)
                                    .zoom(12)
                                    .build()));
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void updateDependencies() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMapView = (MapView) findViewById(R.id.google_map);
    }

    private boolean checkPermission() {
        return !(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }
}
