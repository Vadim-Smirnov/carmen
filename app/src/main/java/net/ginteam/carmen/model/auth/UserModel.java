package net.ginteam.carmen.model.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene on 12/28/16.
 */

public class UserModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("name")
    private String mName;

    public int getId() {
        return mId;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getName() {
        return mName;
    }

}