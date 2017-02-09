package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class PointModel(@SerializedName("lat") val latitude: Double,
                      @SerializedName("lng") val longitude: Double) : Serializable