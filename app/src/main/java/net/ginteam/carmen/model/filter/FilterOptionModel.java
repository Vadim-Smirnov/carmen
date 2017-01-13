package net.ginteam.carmen.model.filter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene on 1/13/17.
 */

public class FilterOptionModel {

    @SerializedName("key")
    private String mKey;

    @SerializedName("value")
    private String mValue;

    public String getKey() {
        return mKey;
    }

    public String getValue() {
        return mValue;
    }

}
