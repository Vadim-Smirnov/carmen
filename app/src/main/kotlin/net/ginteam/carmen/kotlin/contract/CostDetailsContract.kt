package net.ginteam.carmen.kotlin.contract

import android.widget.EditText
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import java.util.*

/**
 * Created by eugene_shcherbinock on 3/20/17.
 */

object CostDetailsActivityContract {

    interface View : BaseContract.View {

        fun setCostInformation(cost: CostTypeModel)
        fun setHistoryInformation(history: HistoryModel, attributesHistory: MutableList <AttributesHistoryModel>)
        fun close()

    }

    interface Presenter <in V : View> : BaseContract.Presenter <V> {

        fun fetchHistoryById(id: Long)
        fun fetchCostById(id: Long)

    }

}

object FuelDetailsActivityContract {

    interface View : CostDetailsActivityContract.View

    interface Presenter : CostDetailsActivityContract.Presenter <View> {

        fun saveFuelHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double,
                            attributes: List <EditText>)

        fun updateFuelHistory(date: Date, odometer: Int, comment: String, price: Double,
                              attributes: List <EditText>, attributesHistory: MutableList<AttributesHistoryModel>)

    }

}