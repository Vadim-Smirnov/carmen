package net.ginteam.carmen.model.company;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vadik on 16.01.17.
 */

public class Detail {

    @SerializedName("phones")
    private List<String> mPhones;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("website")
    private String mWebsite;

    @SerializedName("work_time")
    private List<String> mWorkTime;

    @SerializedName("desc")
    private String mDesc;

    public List<String> getPhones() {
        return mPhones;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public List<String> getWorkTime() {
        return mWorkTime;
    }

    public String getDesc() {
        return mDesc;
    }
}
