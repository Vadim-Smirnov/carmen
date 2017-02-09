package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class FilterOptionModel(val ket: String,
                             val value: String)

data class FilterModel(val name: String,
                       @SerializedName("filter_field") val filterField: String,
                       @SerializedName("dialog") val filterOptions: List <FilterOptionModel>)