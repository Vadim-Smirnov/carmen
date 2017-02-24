package net.ginteam.carmen.model.company;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import net.ginteam.carmen.model.Point;
import net.ginteam.carmen.model.Rating;
import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.category.CategoryModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompanyModel implements ClusterItem, Serializable {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("picture")
    private List<String> mImageUrl;

    @SerializedName("city_id")
    private int mCityId;

    @SerializedName("rating")
    private int mRating;

    @SerializedName("price_rel")
    private int mPrice;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("is_favorite")
    private boolean mIsFavorite;

    @SerializedName("short_desc")
    private String mShortDescription;

    @SerializedName("comforts")
    private ResponseModel<List<Comfort>> mComforts;

    @SerializedName("detail")
    private ResponseModel<Detail> mDetail;

    @SerializedName("categories")
    private ResponseModel<List<CategoryModel>> mCategory;

    @SerializedName("point")
    private Point mPoint;

    @SerializedName("ratings")
    private ResponseModel<List<Rating>> mRatings;

    @SerializedName("distance")
    private double mDistance;

    @SerializedName("ratingByUser")
    private ResponseModel<List<Rating>> mRatingByUser;

    public List<Rating> getRatingByUser() {
        return mRatingByUser.getData();
    }

    public CompanyModel() {
        mIsFavorite = true;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<String> getImageUrl() {
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

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(mPoint.getLatitude(), mPoint.getLongitude());
    }

    public List<Comfort> getComforts() {
        return mComforts.getData();
    }

    public Detail getDetail() {
        return mDetail.getData();
    }

    public List<CategoryModel> getCategory() {
        return mCategory.getData();
    }

    public List<Rating> getRatings() {
        return mRatings.getData();
    }

    public double getDistance() {
        return mDistance;
    }

}