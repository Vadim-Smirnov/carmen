package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vadik on 18.01.17.
 */

public class SortingModel {

    @SerializedName("name")
    private String mName;

    @SerializedName("type")
    private String mType;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
