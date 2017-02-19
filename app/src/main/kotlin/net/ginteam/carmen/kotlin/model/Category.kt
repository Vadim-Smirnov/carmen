package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */
data class CategoryModel(val id: Int,
                         val name: String,
                         val active: Boolean,
                         @SerializedName("companies_count") val companiesCount: Int,
                         @SerializedName("service") val services: List <ServiceModel>) : Serializable