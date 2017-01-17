package net.ginteam.carmen.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.MapContract;
import net.ginteam.carmen.presenter.MapPresenter;
import net.ginteam.carmen.view.fragment.city.CityListFragment;

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
}
