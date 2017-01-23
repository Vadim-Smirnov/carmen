package net.ginteam.carmen.model.city;

import com.google.gson.annotations.SerializedName;

import net.ginteam.carmen.model.Point;

/**
 * Created by Eugene on 12/27/16.
 */

public class CityModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("active")
    private boolean mIsActive;

    @SerializedName("point")
    private Point mPoint;

    public Point getPoint() {
        return mPoint;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public boolean isActive() {
        return mIsActive;
    }

}