package net.ginteam.carmen.kotlin.presenter.costs

import io.realm.Realm
import net.ginteam.carmen.kotlin.Constants
import net.ginteam.carmen.kotlin.contract.CostDetailsActivityContract
import net.ginteam.carmen.kotlin.contract.ServiceDetailsActivityContract
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeAttributeModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.view.custom.FilterEditText
import java.util.*

/**
 * Created by vadimsmirnov on 05.04.17.
 */

class CostDetailsActivityPresenter : BaseCostDetailsActivityPresenter <CostDetailsActivityContract.View>(),
        CostDetailsActivityContract.Presenter {

    override fun saveFuelHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double, attributes: List<FilterEditText>) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        // create managed history object
        val history: HistoryModel = realm.createObject(HistoryModel::class.java)

        val lastId: Number? = realm.where(HistoryModel::class.java).max(Constants.Realm.History.ID)
        history.id = if (lastId == null) 0 else lastId.toLong() + 1
        history.date = date
        history.odometer = odometer
        history.comment = comment
        history.price = price
        history.costType = cost

        for (i in 0 until attributes.size) {
            val editText = attributes[i]
            val attribute = editText.tag as CostTypeAttributeModel
            val attributeHistory: AttributesHistoryModel = realm.createObject(AttributesHistoryModel::class.java)

            val lastAttributeId: Number? = realm.where(AttributesHistoryModel::class.java).max(Constants.Realm.AttributesHistory.ID)
            attributeHistory.id = if (lastAttributeId == null) 0 else lastAttributeId.toLong() + 1
            attributeHistory.value = editText.text.toString()
            attributeHistory.costAttribute = attribute
            attributeHistory.history = history
        }
        realm.commitTransaction()
        realm.close()
        mView?.close()    }

    override fun updateFuelHistory(date: Date, odometer: Int, comment: String, price: Double, attributes: List<FilterEditText>, attributesHistory: MutableList<AttributesHistoryModel>) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        val updateHistory: HistoryModel = attributesHistory.first().history!!
        updateHistory.date = date
        updateHistory.odometer = odometer
        updateHistory.comment = comment
        updateHistory.price = price

        for (i in 0 until attributesHistory.size) {
            val editText = attributes[i]
            val attributeHistory: AttributesHistoryModel = attributesHistory[i]
            attributeHistory.value = editText.text.toString()
        }

        realm.commitTransaction()
        realm.close()
        mView?.close()
    }
}
