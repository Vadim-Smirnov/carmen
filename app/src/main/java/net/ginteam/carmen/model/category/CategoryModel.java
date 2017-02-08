package net.ginteam.carmen.model.category;

import com.google.gson.annotations.SerializedName;

import net.ginteam.carmen.model.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * Created by Eugene on 12/23/16.
 */

public class CategoryModel implements Serializable {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("active")
    private boolean mIsActive;

    @SerializedName("service")
    private List<Service> mServices;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public boolean isActive() {
        return mIsActive;
    }

    public List<Service> getServices() {
        return mServices;
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