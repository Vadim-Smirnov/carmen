package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("total_pages")
    private int mTotalPages;

    @SerializedName("total")
    private int mTotalItemsCount;

    public int getTotalPages() {
        return mTotalPages;
    }

    public int getTotalItemsCount() {
        return mTotalItemsCount;
    }

}
