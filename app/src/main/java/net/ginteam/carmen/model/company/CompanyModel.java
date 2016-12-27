package net.ginteam.carmen.model.company;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompanyModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("image")
    private String mImageUrl;

    @SerializedName("city_id")
    private int mCityId;

    @SerializedName("rating")
    private int mRating;

    @SerializedName("price_rel")
    private int mPrice;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("short_desc")
    private String mShortDescription;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public int getCityId() {
        return mCityId;
    }

    public int getRating() {
        return mRating;
    }

    public int getPrice() {
        return mPrice;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

}