package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Eugene on 12/23/16.
 */

public class ResponseModel <T> implements Serializable {

    @SerializedName("success")
    private boolean mIsSuccess;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("data")
    private T mData;

    @SerializedName("meta")
    private Meta mMeta;

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public String getMessage() {
        return mMessage;
    }

    public T getData() {
        return mData;
    }

    public Meta getMetaData() {
        return mMeta;
    }

}