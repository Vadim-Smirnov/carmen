package net.ginteam.carmen.model.company;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vadik on 16.01.17.
 */

public class Comfort implements Serializable {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("image")
    private String mImage;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getImage() {
        return mImage;
    }
}
