package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vadik on 26.01.17.
 */

public class Service implements Serializable {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("category_id")
    private int mCategoryId;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

}
