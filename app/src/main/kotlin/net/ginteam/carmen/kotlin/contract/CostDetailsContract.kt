package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.model.filter.FilterModel
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

    interface View : BaseCostDetailsActivityContract.View {

        fun showFuelType(fuelTypes: FilterModel)

    }

    interface Presenter : BaseCostDetailsActivityContract.Presenter <View> {

        fun saveFuelHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double,
                            attributes: List <FilterEditText>)

        fun updateFuelHistory(date: Date, odometer: Int, comment: String, price: Double,
                              attributes: List <FilterEditText>, attributesHistory: MutableList<AttributesHistoryModel>)

        fun fetchFuelType()

    }

}

object CarWashDetailsActivityContract {

    interface View : BaseCostDetailsActivityContract.View

    interface Presenter : BaseCostDetailsActivityContract.Presenter <View> {

        fun saveCarWashHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double)

        fun updateCarWashHistory(date: Date, odometer: Int, comment: String, price: Double, history: HistoryModel)

    }

}

object ServiceDetailsActivityContract {

    interface View : BaseCostDetailsActivityContract.View

    interface Presenter : BaseCostDetailsActivityContract.Presenter <View> {

        fun saveServiceHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double,
                            attributes: List <FilterEditText>)

        fun updateServiceHistory(date: Date, odometer: Int, comment: String, price: Double,
                              attributes: List <FilterEditText>, attributesHistory: MutableList<AttributesHistoryModel>)

    }

}

object CostDetailsActivityContract {

    interface View : BaseCostDetailsActivityContract.View

    interface Presenter : BaseCostDetailsActivityContract.Presenter <View> {

        fun saveCostHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double,
                            attributes: List <FilterEditText>)

        fun updateCostHistory(date: Date, odometer: Int, comment: String, price: Double,
                              attributes: List <FilterEditText>, attributesHistory: MutableList<AttributesHistoryModel>)

    }

}