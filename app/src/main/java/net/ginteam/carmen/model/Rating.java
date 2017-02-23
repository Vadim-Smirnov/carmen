package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vadik on 24.01.17.
 */

public class Rating implements Serializable {

    @SerializedName("id")
    private int mId;

    @SerializedName("display_name")
    private String mDisplayName;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("text")
    private String mText;

    @SerializedName("total_rating")
    private float mTotalRating;

    @SerializedName("price_rel")
    private float mPriceRel;

    @SerializedName("answer_name")
    private String mAnswerName;

    @SerializedName("answer_text")
    private String mAnswerText;

    @SerializedName("answer_date")
    private String mAnswerDate;

    @SerializedName("created_at")
    private String mCreatedAt;

    @SerializedName("user_id")
    private int mUserId;

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getId() {
        return mId;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }

    public float getTotalRating() {
        return mTotalRating;
    }

    public float getPriceRel() {
        return mPriceRel;
    }

    public String getAnswerName() {
        return mAnswerName;
    }

    public String getAnswerText() {
        return mAnswerText;
    }

    public String getAnswerDate() {
        return mAnswerDate;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setText(String text) {
        mText = text;
    }

    public void setTotalRating(float totalRating) {
        mTotalRating = totalRating;
    }

    public void setPriceRel(float priceRel) {
        mPriceRel = priceRel;
    }
}
