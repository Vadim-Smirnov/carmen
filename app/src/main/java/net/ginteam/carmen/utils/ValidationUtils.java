package net.ginteam.carmen.utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public class ValidationUtils {

    public static Pair <String, String> validateUserLocation(LatLng location) {
        Pair <String, String> result = new Pair<>();
        result.first = result.second = "0";
        if (location != null) {
            result.first = String.valueOf(location.latitude);
            result.second = String.valueOf(location.longitude);
        }
        return result;
    }

    public static class Pair <T, V> {
        public T first;
        public V second;
    }

}
