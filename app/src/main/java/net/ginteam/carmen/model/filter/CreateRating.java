package net.ginteam.carmen.model.filter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vadik on 20.02.17.
 */

public class CreateRating {

    @SerializedName("company_id")
    private int mCompanyId;

    @SerializedName("user_id")
    private int mUserId;

    @SerializedName("total_rating")
    private float mRating;

    public int getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(int companyId) {
        mCompanyId = companyId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }
}
