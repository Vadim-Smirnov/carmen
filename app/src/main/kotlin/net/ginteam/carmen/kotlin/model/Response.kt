package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class PaginationModel(@SerializedName("total_pages") val totalPages: Int,
                           @SerializedName("total") val totalItems: Int)

data class MetaModel(val pagination: PaginationModel)

data class ResponseModel<out T>(val success: Boolean,
                                val message: String,
                                val data: T,
                                val meta: MetaModel? = null) : Serializable