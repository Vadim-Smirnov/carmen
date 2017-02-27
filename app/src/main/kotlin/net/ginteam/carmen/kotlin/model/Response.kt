package net.ginteam.carmen.kotlin.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.IOException
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class PaginationModel(@SerializedName("total_pages") val totalPages: Int,
                           @SerializedName("total") val totalItems: Int)

data class MetaModel(val pagination: PaginationModel)

data class ResponseModel<out T>(val data: T,
                                val meta: MetaModel? = null) : Serializable

data class ErrorModel(val success: Boolean,
                      val message: String) : Serializable {

    companion object {

        val UNKNOWN_ERROR = 0

        private val gson = Gson()

        fun parseError(json: String): ErrorModel? {
            return try {
                gson.fromJson(json, ErrorModel::class.java)
            } catch (ignored: IOException) {
                null
            }
        }
    }
}