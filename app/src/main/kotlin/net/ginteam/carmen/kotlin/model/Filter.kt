package net.ginteam.carmen.kotlin.model

import net.ginteam.carmen.model.filter.FilterModel

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class FilterOptionModel(val ket: String,
                             val value: String)

class FilterModel : FilterModel()