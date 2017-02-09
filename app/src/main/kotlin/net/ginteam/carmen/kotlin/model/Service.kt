package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */
data class ServiceModel(val id: Int, val name: String,
                        @SerializedName("category_id") val categoryId : Int) : Serializable