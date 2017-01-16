package net.ginteam.carmen.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene on 12/23/16.
 */

public class ResponseModel <T> {

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

//"meta": {
//    "pagination": {
//        "total": 12,
//        "count": 2,
//        "per_page": 2,
//        "current_page": 1,
//        "total_pages": 6,
//        "links": {
//            "next": "http://carmen.b4u.com.ua/api/v1/catalog/companies?limit=2&page=2"
//        }
//    }
//}