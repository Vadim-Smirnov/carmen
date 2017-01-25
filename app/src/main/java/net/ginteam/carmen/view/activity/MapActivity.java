package net.ginteam.carmen.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

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
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListHorizontalItemDecorator;

import java.util.List;

public class MapActivity extends ToolbarActivity implements MapContract.View, OnMapReadyCallback, ClusterManager.OnClusterItemClickListener<CompanyModel>, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener {

    public static final String CATEGORY_ID_ARG = "category_id";

    private MapContract.Presenter mPresenter;

    private ClusterManager<CompanyModel> mClusterManager;
    private GoogleMap mGoogleMap;

    private MapView mMapView;
    private Button mButtonSearch;
    private Button mButtonFilters;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerViewCompanies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_map);

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
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        updateMapDependencies();

        mPresenter.getLastUserLocation();
    }

    @Override
    public boolean onClusterItemClick(CompanyModel companyModel) {
        return false;
    }

    @Override
    public void onCameraMoveStarted(int i) {
        showSearchControl(false);
    }

    @Override
    public void onCameraIdle() {
        showSearchControl(true);
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
                    mPresenter.fetchCompaniesByBounds(mGoogleMap.getProjection().getVisibleRegion().latLngBounds);
                }
            });
        }
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
    public void showCityListView() {
    }

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
    public void showCompanies(List<CompanyModel> companies) {
        mClusterManager.clearItems();
        mClusterManager.addItems(companies);
    }

    @Override
    public void showSearchControl(boolean show) {
        showCompaniesControls(show);

        if (show) {
            mButtonSearch.animate().alpha(1).start();
            return;
        }
        mButtonSearch.animate().alpha(0).start();
    }

    @Override
    public void showCompaniesControls(boolean show) {
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) mRecyclerViewCompanies.getLayoutParams();
        int recyclerViewMargin = layoutParams.bottomMargin;

        Log.d("MAP", "Margin: " + recyclerViewMargin + "; Height: " + mRecyclerViewCompanies.getHeight());

        if (show) {
            mRecyclerViewCompanies
                    .animate()
                    .translationY(mRecyclerViewCompanies.getHeight() + recyclerViewMargin)
                    .setInterpolator(new LinearInterpolator())
                    .start();
            mButtonFilters.animate().alpha(1).start();
            return;
        }
        mRecyclerViewCompanies.animate().translationY(0).setInterpolator(new LinearInterpolator()).start();
        mButtonFilters.animate().alpha(0).start();
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
        mButtonSearch = (Button) findViewById(R.id.button_repeat_search);
        mButtonFilters = (Button) findViewById(R.id.button_filters);

        mRecyclerViewCompanies = (RecyclerView) findViewById(R.id.recycler_view_companies);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCompanies.addItemDecoration(new CompanyRecyclerListHorizontalItemDecorator(getContext(), R.dimen.company_item_spacing));
        mRecyclerViewCompanies.setLayoutManager(mLayoutManager);
    }

    private void updateMapDependencies() {
        mClusterManager = new ClusterManager<>(getContext(), mGoogleMap);
        mClusterManager.setRenderer(new CompanyClusterRenderer(getContext(), mGoogleMap, mClusterManager));
        mClusterManager.setOnClusterItemClickListener(this);
        mGoogleMap.setOnCameraIdleListener(this);
        mGoogleMap.setOnCameraMoveStartedListener(this);
        mGoogleMap.setOnMarkerClickListener(mClusterManager);

        UiSettings googleMapUiSettings = mGoogleMap.getUiSettings();
        googleMapUiSettings.setCompassEnabled(false);
        googleMapUiSettings.setAllGesturesEnabled(true);
        googleMapUiSettings.setMyLocationButtonEnabled(false);
    }

    private boolean checkPermission() {
        return !(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

}
