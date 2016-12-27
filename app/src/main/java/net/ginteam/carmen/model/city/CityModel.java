package net.ginteam.carmen.model.city;

import com.google.gson.annotations.SerializedName;

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