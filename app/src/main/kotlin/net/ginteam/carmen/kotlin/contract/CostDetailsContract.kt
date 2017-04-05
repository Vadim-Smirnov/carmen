package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.view.custom.FilterEditText
import java.util.*

/**
 * Created by eugene_shcherbinock on 3/20/17.
 */

object BaseCostDetailsActivityContract {

    interface View : BaseContract.View {

        fun setCostInformation(cost: CostTypeModel)
        fun setHistoryInformation(history: HistoryModel, attributesHistory: MutableList <AttributesHistoryModel>)
        fun setHistoryInformation(history: HistoryModel)
        fun close()

    }

    interface Presenter <in V : View> : BaseContract.Presenter <V> {

        fun fetchHistoryById(id: Long)
        fun fetchHistoryByIdWithOutAttribute(id: Long)
        fun fetchCostById(id: Long)

    }

}

object FuelDetailsActivityContract {

    interface View : BaseCostDetailsActivityContract.View

    interface Presenter : BaseCostDetailsActivityContract.Presenter <View> {

        fun saveFuelHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double,
                            attributes: List <FilterEditText>)

        fun updateFuelHistory(date: Date, odometer: Int, comment: String, price: Double,
                              attributes: List <FilterEditText>, attributesHistory: MutableList<AttributesHistoryModel>)

    }

}

object CarWashDetailsActivityContract {

    interface View : BaseCostDetailsActivityContract.View

    interface Presenter : BaseCostDetailsActivityContract.Presenter <View> {

        fun saveFuelHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double,
                            attributes: List <FilterEditText>)

        fun updateFuelHistory(date: Date, odometer: Int, comment: String, price: Double,
                              attributes: List <FilterEditText>, attributesHistory: MutableList<AttributesHistoryModel>)

    }

}

object ServiceDetailsActivityContract {

    interface View : BaseCostDetailsActivityContract.View

    interface Presenter : BaseCostDetailsActivityContract.Presenter <View> {

        fun saveFuelHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double,
                            attributes: List <FilterEditText>)

        fun updateFuelHistory(date: Date, odometer: Int, comment: String, price: Double,
                              attributes: List <FilterEditText>, attributesHistory: MutableList<AttributesHistoryModel>)

    }

}

object CostDetailsActivityContract {

    interface View : BaseCostDetailsActivityContract.View

    interface Presenter : BaseCostDetailsActivityContract.Presenter <View> {

        fun saveFuelHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double,
                            attributes: List <FilterEditText>)

        fun updateFuelHistory(date: Date, odometer: Int, comment: String, price: Double,
                              attributes: List <FilterEditText>, attributesHistory: MutableList<AttributesHistoryModel>)

    }

}