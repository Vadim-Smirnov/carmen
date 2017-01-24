package net.ginteam.carmen.model.filter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eugene on 1/13/17.
 */

public class FilterModel {

    @SerializedName("name")
    private String mName;

    @SerializedName("filter_field")
    private String mFilterField;

    @SerializedName("dialog")
    private List<FilterOptionModel> mFilterOptions;

    public String getName() {
        return mName;
    }

    public String getFilterField() {
        return mFilterField;
    }

    public List<FilterOptionModel> getFilterOptions() {
        return mFilterOptions;
    }

}