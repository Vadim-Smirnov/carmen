package net.ginteam.carmen.manager;

import android.Manifest;
import android.app.Activity;
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

    private AppCompatActivity mActivity;

    private OnReceiveLocationListener mLocationListener;

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private PendingResult<LocationSettingsResult> mLocationSettingsResult;

    private ApiGoogleManager(AppCompatActivity activity) {
        mActivity = activity;

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
    }

    public static ApiGoogleManager getInstance(AppCompatActivity activity) {
        if (sInstance == null) {
            sInstance = new ApiGoogleManager(activity);
        }
        return sInstance;
    }

    @Override
    @SuppressWarnings("all")
    public void onConnected(@Nullable Bundle bundle) {
        initializeLocationRequest();

        mLocationSettingsResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
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
                            status.startResolutionForResult(mActivity, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException ignored) {}
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
        Log.d("ApiGoogleManager", "Location changed: " + location);
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mLocationListener.onLocationReceived(mLastLocation);
    }

    public void onActivityResult(int requestCode, int resultCode) {
        if (requestCode == REQUEST_LOCATION && resultCode == Activity.RESULT_OK) {
            startLocationUpdate();
            return;
        }
        mLocationListener.onLocationReceiveFailure();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 500) {
            boolean permissionsGranted;
            for (int currentResult : grantResults) {
                permissionsGranted = (currentResult == PackageManager.PERMISSION_GRANTED);
                if (!permissionsGranted) {
                    Log.d("ApiGoogleManager", "onRequestPermissionsResult - granted");
                    mLocationListener.onLocationReceiveFailure();
                    return;
                }
            }
            Log.d("ApiGoogleManager", "onRequestPermissionsResult startLocationUpdate");
            startLocationUpdate();
        }
    }

    public void getLastLocation(OnReceiveLocationListener listener) {
        mLocationListener = listener;
        mGoogleApiClient.connect();
    }

    private void initializeLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        mLocationSettingsResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
    }

    private void startLocationUpdate() {
        Log.d("ApiGoogleManager", "startLocationUpdate");
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(0)
                .setFastestInterval(0);

        if (this.checkPermission()) {
            Log.d("ApiGoogleManager", "startLocationUpdate - permission good");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
