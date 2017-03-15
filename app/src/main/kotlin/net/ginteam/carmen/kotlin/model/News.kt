package net.ginteam.carmen.kotlin.model

import com.google.gson.annotations.SerializedName
import net.ginteam.carmen.kotlin.view.adapter.company.BaseCompaniesAdapter
import net.ginteam.carmen.kotlin.view.adapter.news.BaseNewsAdapter
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

class NewsModel : Serializable {
    val id: Int = 0
    val title: String = ""
    val image: String = ""
    val text: String = ""
    val views: Int = 0
    @SerializedName("is_favorite") var isFavorite: Boolean = false
    @SerializedName("source_name") val source: String = ""
    @SerializedName("publication_date") val publicationDate: String = ""
    // for transitions
    @Transient var transitionViewHolder: BaseNewsAdapter.ViewHolder? = null
}