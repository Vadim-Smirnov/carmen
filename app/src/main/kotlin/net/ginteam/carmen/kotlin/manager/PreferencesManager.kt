package net.ginteam.carmen.kotlin.manager

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.kotlin.Constants
import net.ginteam.carmen.kotlin.model.CityModel

/**
 * Created by eugene_shcherbinock on 2/14/17.
 */

interface PreferencesManager {

    var userAccessToken: String
    var userCityModel: CityModel?
    var userLocation: LatLng?

}

object SharedPreferencesManager : PreferencesManager {

    private val mPreferences
            = CarmenApplication
            .getContext()
            .getSharedPreferences(Constants.Preferences.NAME, Context.MODE_PRIVATE)

    override var userAccessToken: String
        get() = mPreferences.getString(Constants.Preferences.USER_TOKEN, "")
        set(value) {
            mPreferences.edit().putString(Constants.Preferences.USER_TOKEN, value).apply()
        }

    override var userCityModel: CityModel?
        get() {
            val cityJson = mPreferences.getString(Constants.Preferences.CITY, "")
            Log.d("SharedPrefsManager", "Get json model: $cityJson")
            return Gson().fromJson(cityJson, CityModel::class.java)
        }
        set(value) {
            val cityJson = Gson().toJson(value)
            Log.d("SharedPrefsManager", "Set json model: $cityJson")
            mPreferences.edit().putString(Constants.Preferences.CITY, cityJson).apply()
        }

    override var userLocation: LatLng?
        get() {
            val locationString = mPreferences.getString(Constants.Preferences.USER_LOCATION, "")
            val location: LatLng? = if (locationString.isEmpty()) {
                null
            } else {
                LatLng(
                        locationString.split(" ")[0].toDouble(),
                        locationString.split(" ")[1].toDouble()
                )
            }
            return location
        }
        set(value) {
            val locationString = String.format("%s %s", value?.latitude ?: "0", value?.longitude ?: "0")
            mPreferences.edit().putString(Constants.Preferences.USER_LOCATION, locationString).apply()
        }
}