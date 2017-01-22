package net.ginteam.carmen.view.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.MapContract;
import net.ginteam.carmen.presenter.MapPresenter;

public class MapActivity extends ToolbarActivity implements MapContract.View {

    public static final String CATEGORY_ID_ARG = "category_id";

    private MapContract.Presenter mPresenter;

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        updateDependencies();

        mMapView.onCreate(savedInstanceState);

        mPresenter = new MapPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showGoogleMap(final Location userLocation) {
        mMapView.onResume();
        if (userLocation != null) {
            mMapView.post(new Runnable() {
                @Override
                public void run() {
                    mPresenter
                            .animateToLocation(new LatLng(userLocation.getLatitude(),
                                    userLocation.getLongitude()));
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
       Toast.makeText(getContext(), "Show city list", Toast.LENGTH_SHORT).show();
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

}
