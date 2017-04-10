package net.ginteam.carmen.kotlin.presenter.costs

import io.realm.Realm
import net.ginteam.carmen.kotlin.Constants
import net.ginteam.carmen.kotlin.contract.CarWashDetailsActivityContract
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeAttributeModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.view.custom.FilterEditText
import java.util.*

/**
 * Created by vadimsmirnov on 04.04.17.
 */

class CarWashDetailsActivityPresenter : BaseCostDetailsActivityPresenter <CarWashDetailsActivityContract.View>(),
        CarWashDetailsActivityContract.Presenter {

    override fun saveCarWashHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double) {
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

        realm.commitTransaction()
        realm.close()
        mView?.close()
    }

    override fun updateCarWashHistory(date: Date, odometer: Int, comment: String, price: Double, history: HistoryModel) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        val updateHistory: HistoryModel = history
        updateHistory.date = date
        updateHistory.odometer = odometer
        updateHistory.comment = comment
        updateHistory.price = price

        realm.commitTransaction()
        realm.close()
        mView?.close()
    }
}