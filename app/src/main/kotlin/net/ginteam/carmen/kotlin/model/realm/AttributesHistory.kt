package net.ginteam.carmen.kotlin.model.realm

import io.realm.RealmObject

/**
 * Created by eugene_shcherbinock on 3/16/17.
 */

open class AttributesHistoryModel : RealmObject() {

    open var id: Long = 0
    open var value: String = ""

    open var costAttribute: CostTypeAttributeModel? = null
    open var history: HistoryModel? = null

}