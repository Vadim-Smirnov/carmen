package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vadik on 24.01.17.
 */

public class Rating {

    @SerializedName("id")
    private int mId;

    @SerializedName("display_name")
    private String mDisplayName;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("text")
    private String mText;

    @SerializedName("total_rating")
    private int mTotalRating;

    @SerializedName("price_rel")
    private int mPriceRel;

    @SerializedName("answer_name")
    private String mAnswerName;

    @SerializedName("answer_text")
    private String mAnswerText;

    @SerializedName("answer_date")
    private String mAnswerDate;

    @SerializedName("created_at")
    private String mCreatedAt;

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

    public int getTotalRating() {
        return mTotalRating;
    }

    public int getPriceRel() {
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
}
