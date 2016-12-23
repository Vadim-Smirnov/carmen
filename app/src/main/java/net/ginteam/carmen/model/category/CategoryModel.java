package net.ginteam.carmen.model.category;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * Created by Eugene on 12/23/16.
 */

public class CategoryModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("active")
    private boolean mIsActive;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public boolean isActive() {
        return mIsActive;
    }

    @Override
    public String toString() {
        return String
                .format(
                        Locale.getDefault(),
                        "ID: %d;\nName: %s;\nActive: %s;\n",
                        mId, mName, mIsActive + ""
                );
    }
}