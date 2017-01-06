package net.ginteam.carmen.manager;

import android.content.Context;
import android.content.SharedPreferences;

import net.ginteam.carmen.CarmenApplication;
import net.ginteam.carmen.common.Constants;

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

}
