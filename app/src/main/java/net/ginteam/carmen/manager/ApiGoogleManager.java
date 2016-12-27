package net.ginteam.carmen.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by vadik on 27.12.16.
 */

public class ApiGoogleManager implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private static final int REQUEST_LOCATION = 199;

    private static ApiGoogleManager sInstance;

    private Context mContext;
    private AppCompatActivity mActivity;

    private OnReceiveLocationListener mLocationListener;

    private Status mStatus;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private PendingResult<LocationSettingsResult> mLocationSettingsResult;

    private ApiGoogleManager(Context context, AppCompatActivity activity) {
        mContext = context;
        mActivity = activity;

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
    }

    public static ApiGoogleManager getInstance(Context context, AppCompatActivity activity) {
        if (sInstance == null) {
            sInstance = new ApiGoogleManager(context, activity);
        }
        return sInstance;
    }

    @Override
    @SuppressWarnings("all")
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        mLocationSettingsResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        mLocationSettingsResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                mStatus = locationSettingsResult.getStatus();
                switch (mStatus.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        if (mLastLocation != null) {
                            if (mLocationListener != null) {
                                mLocationListener.onLocationReceived(mLastLocation);
                            }
                            return;
                        } else {
                            startLocationUpdate();
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            mStatus.startResolutionForResult(mActivity, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException ignored) {}
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mLocationListener.onLocationReceived(mLastLocation);
    }

    public void onActivityResult(int requestCode, int resultCode) {
        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        startLocationUpdate();
                        break;
                    }
                    default:
                }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 500) {
            boolean permissionsGranted = true;
            for (int currentResult : grantResults) {
                permissionsGranted = (currentResult == PackageManager.PERMISSION_GRANTED);
                if (!permissionsGranted) {
                    return;
                }
            }

            if (permissionsGranted) {
                startLocationUpdate();
            } else {
//                mPresenter.getCityList();
            }
        }
    }

    public void getLastLocation(OnReceiveLocationListener listener) {
        mLocationListener = listener;
        mGoogleApiClient.connect();
    }

    private void startLocationUpdate() {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(0)
                .setFastestInterval(0);
        // Request location updates
        if (this.checkPermission()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        ActivityCompat.requestPermissions(
                mActivity,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                },
                500);
        return false;
    }

    public interface OnReceiveLocationListener {

        void onLocationReceived(Location location);

        void onLocationReceiveFailure();

    }

}
