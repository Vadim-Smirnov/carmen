package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class RatingModel(val id: Int,
                       @SerializedName("display_name") val displayName: String,
                       val title: String,
                       val text: String,
                       @SerializedName("total_rating") val totalRating: Int,
                       @SerializedName("price_rel") val price: Int,
                       @SerializedName("answer_name") val answerName: String,
                       @SerializedName("answer_text") val answerText: String,
                       @SerializedName("answer_date") val answerDate: String,
                       @SerializedName("created_at") val createdAt: String,
                       @SerializedName("user_id") val userId: Int) : Serializable

data class InitialRating(@SerializedName("company_id") val companyId: Int,
                         @SerializedName("total_rating") val totalRating: Float) : Serializable