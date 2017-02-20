package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class SortModel(var name: String,
                     @SerializedName("sort_field") var sortField: String,
                     @SerializedName("sort_type") var sortType: String)