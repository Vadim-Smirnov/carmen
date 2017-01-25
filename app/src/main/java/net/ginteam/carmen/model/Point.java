package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vadik on 23.01.17.
 */

public class Point implements Serializable {

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
