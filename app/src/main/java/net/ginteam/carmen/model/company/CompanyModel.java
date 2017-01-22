package net.ginteam.carmen.model.company;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.category.CategoryModel;

import java.util.List;
import java.util.Random;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompanyModel implements ClusterItem {

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

    public CompanyModel() {
        mIsFavorite = true;
    }

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

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }

    @Override
    public LatLng getPosition() {
        Random generator = new Random();
        double lat, lng;
        lat = generator.nextDouble() / 3;
        lng = generator.nextDouble() / 3;
        if (generator.nextBoolean()) {
            lat = -lat;
        }
        if (generator.nextBoolean()) {
            lng = -lng;
        }
        return new LatLng(lat, lng);
    }

    public ResponseModel<List<Comfort>> getComforts() {
        return mComforts;
    }

    public ResponseModel<Detail> getDetail() {
        return mDetail;
    }

    public ResponseModel<List<CategoryModel>> getCategory() {
        return mCategory;
    }

}