//package net.ginteam.carmen.view.activity.map;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.animation.LinearInterpolator;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.UiSettings;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.maps.android.clustering.Cluster;
//import com.google.maps.android.clustering.ClusterManager;
//
//import net.ginteam.carmen.R;
//import net.ginteam.carmen.contract.MapContract;
//import net.ginteam.carmen.model.company.CompanyModel;
//import net.ginteam.carmen.model.company.MapCompanyModel;
//import net.ginteam.carmen.presenter.MapPresenter;
//import net.ginteam.carmen.utils.ActivityUtils;
//import net.ginteam.carmen.view.activity.ToolbarActivity;
//import net.ginteam.carmen.view.activity.company.CompanyDetailActivity;
//import net.ginteam.carmen.view.activity.filter.FilterActivity;
//import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListHorizontalItemDecorator;
//import net.ginteam.carmen.view.adapter.company.map.CompanyItemViewHolder;
//import net.ginteam.carmen.view.adapter.company.map.CompanyRecyclerListAdapter;
//
//import java.util.Collection;
//import java.util.List;
//
//public class MapActivity extends ToolbarActivity implements MapContract.View, OnMapReadyCallback,
//        ClusterManager.OnClusterItemClickListener<MapCompanyModel>, GoogleMap.OnCameraIdleListener,
//        GoogleMap.OnCameraMoveStartedListener, View.OnClickListener,
//        CompanyItemViewHolder.OnCompanyItemClickListener,
//        ClusterManager.OnClusterClickListener<MapCompanyModel> {
//
//    public static final String CATEGORY_ID_ARG = "category_id";
//
//    private MapContract.Presenter mPresenter;
//
//    private int mCategoryId;
//    private String mFilters;
//    private boolean mIsNeedAutomaticallyFetch;
//
//    private GoogleMap mGoogleMap;
//    private ClusterManager<MapCompanyModel> mClusterManager;
//    private MapCompanyModel mSelectedCompany;
//
//    private MapView mMapView;
//    private Button mButtonSearch;
//    private Button mButtonFilters;
//
//    private LinearLayoutManager mLayoutManager;
//    private RecyclerView mRecyclerViewCompanies;
//    private CompanyRecyclerListAdapter mRecyclerListAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        super.setContentView(R.layout.activity_map);
//
//        updateDependencies();
//
//        mMapView.onCreate(savedInstanceState);
//
//        mPresenter = new MapPresenter();
//        mPresenter.attachView(this);
//
//        mMapView.getMapAsync(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mPresenter.detachView();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.button_repeat_search) {
//            mGoogleMap.clear();
//            mPresenter.fetchCompaniesByBounds(
//                    mCategoryId,
//                    mFilters,
//                    getCurrentMapBounds()
//            );
//            return;
//        }
//        startFilterActivityForResult(mCategoryId);
//    }
//
//    @Override
//    public void onCompanyItemClick(CompanyModel company) {
//        Bundle arguments = new Bundle();
//        arguments.putInt(CompanyDetailActivity.COMPANY_ID_ARGUMENT, company.getId());
//        ActivityUtils.showActivity(CompanyDetailActivity.class, arguments, false);
//        finish();
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mGoogleMap = googleMap;
//        updateMapDependencies();
//
//        mPresenter.getLastUserLocation();
//    }
//
//    @Override
//    public boolean onClusterClick(Cluster<MapCompanyModel> cluster) {
//        fitMapBoundsWithClusterItems(cluster.getItems(), 30);
//        return true;
//    }
//
//    @Override
//    public boolean onClusterItemClick(MapCompanyModel companyModel) {
//        if (mSelectedCompany != null) {
//            mSelectedCompany.setSelected(false);
//            ((CompanyClusterRenderer) mClusterManager.getRenderer()).updateClusterItem(mSelectedCompany);
//        }
//
//        if (!companyModel.isSelected()) {
//            mSelectedCompany = companyModel;
//            mSelectedCompany.setSelected(true);
//            ((CompanyClusterRenderer) mClusterManager.getRenderer()).updateClusterItem(mSelectedCompany);
//        }
//
//        int selectionPosition = mRecyclerListAdapter.selectItem(mSelectedCompany);
//        mRecyclerViewCompanies.smoothScrollToPosition(selectionPosition);
//
//        return false;
//    }
//
//    @Override
//    public void onCameraMoveStarted(int i) {
//        showSearchControl(false);
//    }
//
//    @Override
//    public void onCameraIdle() {
//        mClusterManager.onCameraIdle();
//        showSearchControl(true);
//
//        if (mIsNeedAutomaticallyFetch) {
//            mPresenter.fetchCompaniesByBounds(
//                    mCategoryId,
//                    mFilters,
//                    getCurrentMapBounds()
//            );
//            mIsNeedAutomaticallyFetch = false;
//        }
//    }
//
//    @Override
//    public void showGoogleMap(final LatLng userLocation) {
//        mMapView.onResume();
//
//        if (userLocation != null) {
//            if (checkPermission()) {
//                mGoogleMap.setMyLocationEnabled(true);
//            }
//            mMapView.post(new Runnable() {
//                @Override
//                public void run() {
//                    mIsNeedAutomaticallyFetch = true;
//                    animateToLocation(userLocation);
//                }
//            });
//        }
//    }
//
//    @Override
//    public MapView getMapView() {
//        return mMapView;
//    }
//
//    @Override
//    public AppCompatActivity getActivity() {
//        return this;
//    }
//
//    @Override
//    public void showCityListView() {
//    }
//
//    @Override
//    public void animateToLocation(LatLng location) {
//        if (location != null) {
//            mGoogleMap.animateCamera(
//                    CameraUpdateFactory.newCameraPosition(
//                            new CameraPosition.Builder()
//                                    .target(location)
//                                    .zoom(12)
//                                    .build()));
//        }
//    }
//
//    @Override
//    public void showCompanies(List<MapCompanyModel> companies) {
//        mClusterManager.clearItems();
//        mClusterManager.addItems(companies);
//        mClusterManager.cluster();
//
//        mRecyclerListAdapter = new CompanyRecyclerListAdapter(getContext(), companies);
//        mRecyclerListAdapter.setOnCompanyItemClickListener(this);
//        mRecyclerViewCompanies.setAdapter(mRecyclerListAdapter);
//    }
//
//    @Override
//    public void showSearchControl(boolean show) {
//        if (show) {
//            mButtonSearch.animate().alpha(1).start();
//            return;
//        }
//        mButtonSearch.animate().alpha(0).start();
//    }
//
//    @Override
//    public void showCompaniesControls(boolean show) {
//        if (show) {
//            mRecyclerViewCompanies.setVisibility(View.VISIBLE);
//            mRecyclerViewCompanies
//                    .animate()
//                    .translationY(0)
//                    .setInterpolator(new LinearInterpolator())
//                    .withEndAction(new Runnable() {
//                        @Override
//                        public void run() {
//                            mButtonFilters.animate().alpha(1).start();
//                        }
//                    })
//                    .start();
//            return;
//        }
//        mRecyclerViewCompanies.setVisibility(View.GONE);
//        mRecyclerViewCompanies
//                .animate()
//                .translationY(mRecyclerViewCompanies.getHeight())
//                .setInterpolator(new LinearInterpolator())
//                .withEndAction(new Runnable() {
//                    @Override
//                    public void run() {
//                        mButtonFilters.animate().alpha(0).start();
//                    }
//                })
//                .start();
//    }
//
//    @Override
//    public Context getContext() {
//        return this;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == FilterActivity.REQUEST_CODE && resultCode == RESULT_OK) {
//            mFilters = data.getStringExtra(FilterActivity.RESULT_FILTER_ARGUMENT);
//            mPresenter.fetchCompaniesByBounds(mCategoryId, mFilters, getCurrentMapBounds());
//            Log.d("FilterActivity", mFilters);
//            return;
//        }
//        mPresenter.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    private void fitMapBoundsWithClusterItems(Collection<MapCompanyModel> companies, int zoom) {
//        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
//        for (CompanyModel currentCompany : companies) {
//            boundsBuilder.include(currentCompany.getPosition());
//        }
//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), zoom));
//        mIsNeedAutomaticallyFetch = true;
//    }
//
//    private LatLngBounds getCurrentMapBounds() {
//        return mGoogleMap.getProjection().getVisibleRegion().latLngBounds;
//    }
//
//    private void updateDependencies() {
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_map);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        mIsNeedAutomaticallyFetch = false;
//        mCategoryId = getIntent().getIntExtra(CATEGORY_ID_ARG, 0);
//        mFilters = "";
//
//        mMapView = (MapView) findViewById(R.id.google_map);
//        mButtonSearch = (Button) findViewById(R.id.button_repeat_search);
//        mButtonFilters = (Button) findViewById(R.id.button_filters);
//
//        mButtonSearch.setOnClickListener(this);
//        mButtonFilters.setOnClickListener(this);
//
//        mRecyclerViewCompanies = (RecyclerView) findViewById(R.id.recycler_view_companies);
//        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        mRecyclerViewCompanies.addItemDecoration(new CompanyRecyclerListHorizontalItemDecorator(getContext(), R.dimen.company_item_spacing));
//        mRecyclerViewCompanies.setLayoutManager(mLayoutManager);
//    }
//
//    private void updateMapDependencies() {
//        mClusterManager = new ClusterManager<>(getContext(), mGoogleMap);
//        mClusterManager.setRenderer(new CompanyClusterRenderer(getContext(), mGoogleMap, mClusterManager));
//        mClusterManager.setOnClusterItemClickListener(this);
//        mClusterManager.setOnClusterClickListener(this);
//
//        mGoogleMap.setOnCameraIdleListener(this);
//        mGoogleMap.setOnCameraMoveStartedListener(this);
//        mGoogleMap.setOnMarkerClickListener(mClusterManager);
//
//        UiSettings googleMapUiSettings = mGoogleMap.getUiSettings();
//        googleMapUiSettings.setCompassEnabled(false);
//        googleMapUiSettings.setAllGesturesEnabled(true);
//        googleMapUiSettings.setMyLocationButtonEnabled(false);
//    }
//
//    private void startFilterActivityForResult(int categoryId) {
//        Intent intent = new Intent(this, FilterActivity.class);
//        intent.putExtra(FilterActivity.CATEGORY_ID_ARGUMENT, categoryId);
//        startActivityForResult(intent, FilterActivity.REQUEST_CODE);
//    }
//
//    private boolean checkPermission() {
//        return !(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
//    }
//
//}