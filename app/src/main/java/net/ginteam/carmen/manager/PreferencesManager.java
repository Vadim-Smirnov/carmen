package net.ginteam.carmen.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import net.ginteam.carmen.CarmenApplication;
import net.ginteam.carmen.common.Constants;
import net.ginteam.carmen.model.city.CityModel;

/**
 * Created by Eugene on 12/28/16.
 */

public class PreferencesManager {

    private static PreferencesManager sInstance;

    private SharedPreferences mPreferences;

    private PreferencesManager() {
        mPreferences = CarmenApplication
                .getContext()
                .getSharedPreferences(Constants.PREFERENCES.NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesManager getInstance() {
        if (sInstance == null) {
            sInstance = new PreferencesManager();
        }
        return sInstance;
    }

    public void setUserToken(String token) {
        mPreferences
                .edit()
                .putString(Constants.PREFERENCES.USER_TOKEN, token)
                .apply();
    }

    public String getUserToken() {
        return mPreferences.getString(Constants.PREFERENCES.USER_TOKEN, "");
    }

    public void setCity(String city) {
        mPreferences
                .edit()
                .putString(Constants.PREFERENCES.CITY, city)
                .apply();
    }

    public CityModel getCity() {
        String cityJson = mPreferences.getString(Constants.PREFERENCES.CITY, "");
        return new Gson().fromJson(cityJson, CityModel.class);
    }

    public void setUserLocation(String userLocation) {
        mPreferences
                .edit()
                .putString(Constants.PREFERENCES.USER_LOCATION, userLocation)
                .apply();
    }
//
    public LatLng getUserLocation() {
       String userLocation = mPreferences.getString(Constants.PREFERENCES.USER_LOCATION, "");

        return !userLocation.isEmpty() ? new LatLng(Double.valueOf(userLocation.split(" ")[0]),
                Double.valueOf(userLocation.split(" ")[1])) : null;
    }

}
