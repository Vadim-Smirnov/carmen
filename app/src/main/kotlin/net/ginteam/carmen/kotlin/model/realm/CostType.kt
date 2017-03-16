package net.ginteam.carmen.kotlin.model.realm

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by eugene_shcherbinock on 3/15/17.
 */

open class CostTypeModel : RealmObject() {

    open var id: Long = 0
    open var name: String = ""
    open var iconId: Long = 0
    open var color: Long = 0

    open var attributes: RealmList <CostTypeAttributeModel>? = null

}