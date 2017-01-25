package net.ginteam.carmen.contract;

import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import net.ginteam.carmen.model.company.CompanyModel;

import java.util.List;

/**
 * Created by vadik on 10.01.17.
 */

public interface MapContract {

    interface View extends RequestLocationContract.View {

        void showGoogleMap(LatLng userLocation);

        MapView getMapView();

        AppCompatActivity getActivity();

        void animateToLocation(LatLng location);

        void showCompanies(List <CompanyModel> companies);

        void showSearchControl(boolean show);

        void showCompaniesControls(boolean show);

    }

    interface Presenter extends RequestLocationContract.Presenter<View> {

        void getLastUserLocation();

        void fetchCompaniesByBounds(LatLngBounds bounds);

    }

}
