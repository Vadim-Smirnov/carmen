package net.ginteam.carmen.kotlin.view.activity.costs

import android.util.Log
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.CarWashDetailsActivityContract
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeAttributeModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.presenter.costs.CarWashDetailsActivityPresenter
import java.util.*

/**
 * Created by vadimsmirnov on 04.04.17.
 */

class CarWashDetailsActivity : BaseCostDetailsActivity <CarWashDetailsActivityContract.View,
        CarWashDetailsActivityContract.Presenter>(), CarWashDetailsActivityContract.View {

    override var mPresenter: CarWashDetailsActivityContract.Presenter = CarWashDetailsActivityPresenter()

    override fun getLayoutResId(): Int = R.layout.activity_carwash_details

    override fun setCostInformation(cost: CostTypeModel) {
        super.setCostInformation(cost)

        Log.d("FuelDetailsActivity", "Cost: ${cost.attributes?.size}")
        cost.attributes?.let {
            for (i in 0 until it.size) {
                val currentAttribute: CostTypeAttributeModel = it[i]
                val currentAttributeView = mAttributesViews[i]

                currentAttributeView.hint = currentAttribute.title
                currentAttributeView.tag = currentAttribute
            }
        }
    }

    override fun setHistoryInformation(history: HistoryModel) {
        super.setHistoryInformation(history)
    }

    override fun saveHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double) {
        mPresenter.saveFuelHistory(cost, date, odometer, comment, price, mAttributesViews)
    }

    override fun updateHistory(date: Date, odometer: Int, comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>) {
        mPresenter.updateFuelHistory(date, odometer, comment, price, mAttributesViews, attributesHistory)
    }
}