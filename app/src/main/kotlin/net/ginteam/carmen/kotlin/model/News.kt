package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

class NewsModel : Serializable {
    val id: Int = 0
    val title: String = ""
    val image: String = ""
    var isFavorite: Boolean = false
    @SerializedName("source_name") val source: String = ""
    @SerializedName("publication_date") val publicationDate: String = ""
}