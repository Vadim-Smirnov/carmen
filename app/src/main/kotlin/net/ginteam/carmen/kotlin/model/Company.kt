package net.ginteam.carmen.kotlin.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class ComfortModel(val id: Int,
                        val name: String,
                        val image: String) : Serializable

data class DetailModel(val phones: List <String>,
                       @SerializedName("desc") val description: String,
                       @SerializedName("email") val emails: List <String>,
                       @SerializedName("website") val websites: List <String>,
                       @SerializedName("work_time") val workTimes: List <String>) : Serializable

open class CompanyModel : ClusterItem, Serializable {
    val id: Int = 0
    val name: String = ""
    val address: String = ""
    val rating: Int = 0
    val comforts: ResponseModel <List <ComfortModel>>? = null
    val categories: ResponseModel <List <CategoryModel>>? = null
    internal val point: PointModel? = null
    val ratings: ResponseModel <List <String>>? = null
    val distance: Double = 0.0

    @SerializedName("picture") val pictures: List <String>? = null
    @SerializedName("city_id") val cityId: Int = 0
    @SerializedName("price_rel") val price: Int = 0
    @SerializedName("is_favorite") val isFavorite: Boolean = false
    @SerializedName("short_desc") val shortDescription: String = ""
    @SerializedName("detail") val details: ResponseModel <List <DetailModel>>? = null

    override fun getPosition(): LatLng = LatLng(point!!.latitude, point!!.longitude)

}

open class MapCompanyModel : CompanyModel() {

    val isSelected: Boolean = false

}