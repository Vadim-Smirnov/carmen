package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vadik on 23.01.17.
 */

public class Point {

    @SerializedName("lat")
    private double mLatitude;

    @SerializedName("lng")
    private double mLongitude;

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
}
