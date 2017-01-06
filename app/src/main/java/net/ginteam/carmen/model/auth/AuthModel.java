package net.ginteam.carmen.model.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene on 12/28/16.
 */

public class AuthModel {

    @SerializedName("user")
    private UserModel mUser;

    @SerializedName("token")
    private String mToken;

    public UserModel getUser() {
        return mUser;
    }

    public String getToken() {
        return mToken;
    }

}
