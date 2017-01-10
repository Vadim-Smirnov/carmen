package net.ginteam.carmen.contract;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by vadik on 10.01.17.
 */

public interface MapContract {

    interface View extends FetchContract.View {

        void showGoogleMap(Location userLocation);

        MapView getMapView();

        AppCompatActivity getActivity();

    }

    interface Presenter extends FetchContract.Presenter<View> {

        void prepareGoogleMap();

        void animateToLocation(LatLng location);

    }

}
