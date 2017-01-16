package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("pagination")
    private Pagination mPagination;

    public Pagination getPagination() {
        return mPagination;
    }

}
