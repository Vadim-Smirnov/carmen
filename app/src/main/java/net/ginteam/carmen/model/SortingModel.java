package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vadik on 18.01.17.
 */

public class SortingModel {

    @SerializedName("name")
    private String mName;

    @SerializedName("sort_field")
    private String mSortedField;

    @SerializedName("sort_type")
    private String mSortedType;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSortedField() {
        return mSortedField;
    }

    public void setSortedField(String sortedField) {
        mSortedField = sortedField;
    }

    public String getSortedType() {
        return mSortedType;
    }

    public void setSortedType(String sortedType) {
        mSortedType = sortedType;
    }

}
