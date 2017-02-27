package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class RatingModel(val id: Int,
                       @SerializedName("display_name") var displayName: String,
                       var title: String,
                       var text: String,
                       @SerializedName("total_rating") var totalRating: Float,
                       @SerializedName("price_rel") var price: Int,
                       @SerializedName("answer_name") val answerName: String,
                       @SerializedName("answer_text") val answerText: String,
                       @SerializedName("answer_date") val answerDate: String,
                       @SerializedName("created_at") val createdAt: String,
                       @SerializedName("user_id") val userId: Int) : Serializable

data class InitialRating(@SerializedName("company_id") val companyId: Int,
                         @SerializedName("total_rating") val totalRating: Float) : Serializable