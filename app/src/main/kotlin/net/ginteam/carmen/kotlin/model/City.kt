package net.ginteam.carmen.kotlin.model

import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class CityModel(val id: Int,
                     val name: String,
                     val active: Boolean,
                     val point: PointModel,
                     val bound: String) : Serializable