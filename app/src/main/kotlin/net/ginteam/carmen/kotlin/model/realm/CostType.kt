package net.ginteam.carmen.kotlin.model.realm

import io.realm.RealmList
import io.realm.RealmObject
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 3/15/17.
 */

open class CostTypeModel : RealmObject(), Serializable {

    open var id: Long = 0
    open var name: String = ""
    open var icon: String = ""
    open var color: String = ""

    open var attributes: RealmList <CostTypeAttributeModel>? = null

}