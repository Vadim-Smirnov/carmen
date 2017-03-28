package net.ginteam.carmen.kotlin.model.realm

import io.realm.RealmObject
import java.util.*

/**
 * Created by eugene_shcherbinock on 3/15/17.
 */

open class HistoryModel : RealmObject() {

    open var id: Long = 0
    open var date: Date? = Date()
    open var price: Double = 0.0
    open var odometer: Int = 0
    open var comment: String = ""

    open var costType: CostTypeModel? = null

}