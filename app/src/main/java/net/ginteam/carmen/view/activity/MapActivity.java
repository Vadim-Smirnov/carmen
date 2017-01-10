package net.ginteam.carmen.view.activity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.MapContract;
import net.ginteam.carmen.presenter.MapPresenter;

public class MapActivity extends ToolbarActivity implements MapContract.View {

    private MapContract.Presenter mPresenter;

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mMapView = (MapView) findViewById(R.id.google_map);
        mMapView.onCreate(savedInstanceState);

        mPresenter = new MapPresenter(this);
        mPresenter.prepareGoogleMap();

    }

    @Override
    public void showGoogleMap(final Location userLocation) {
        mMapView.onResume();

        mMapView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter
                        .animateToLocation(new LatLng(userLocation.getLatitude(),
                                userLocation.getLongitude()));
            }
        });
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
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
