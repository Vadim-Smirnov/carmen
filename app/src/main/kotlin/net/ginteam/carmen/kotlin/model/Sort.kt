package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class SortModel(val name: String,
                     @SerializedName("sort_field") val sortField: String,
                     @SerializedName("sort_type") val sortType: String)