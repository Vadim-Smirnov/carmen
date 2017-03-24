package net.ginteam.carmen.kotlin.model.realm

import io.realm.RealmObject
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 3/16/17.
 */

open class CostTypeAttributeModel : RealmObject(), Serializable {

    open var id: Long = 0
    open var title: String = ""
    open var type: String = ""

}