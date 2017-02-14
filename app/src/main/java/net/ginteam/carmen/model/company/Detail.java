package net.ginteam.carmen.model.company;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vadik on 16.01.17.
 */

public class Detail implements Serializable {

    @SerializedName("phones")
    private List<String> mPhones;

    @SerializedName("email")
    private List<String> mEmail;

    @SerializedName("website")
    private List<String> mWebsite;

//    @SerializedName("work_time")
//    private List<String> mWorkTime;

    @SerializedName("desc")
    private String mDesc;

    public List<String> getPhones() {
        return mPhones;
    }

    public List<String> getEmail() {
        return mEmail;
    }

    public List<String> getWebsite() {
        return mWebsite;
    }

//    public List<String> getWorkTime() {
//        return mWorkTime;
//    }

    public String getDesc() {
        return mDesc;
    }
}
