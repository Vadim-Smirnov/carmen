package net.ginteam.carmen.kotlin.view.activity.costs

import android.util.Log
import android.widget.EditText
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.FuelDetailsActivityContract
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeAttributeModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.presenter.costs.FuelDetailsActivityPresenter
import java.util.*

/**
 * Created by eugene_shcherbinock on 3/21/17.
 */

class FuelDetailsActivity : BaseCostDetailsActivity <FuelDetailsActivityContract.View, FuelDetailsActivityContract.Presenter>(),
        FuelDetailsActivityContract.View {

    override var mPresenter: FuelDetailsActivityContract.Presenter = FuelDetailsActivityPresenter()

    private lateinit var mEditTextFuelType: EditText
    private lateinit var mEditTextLiters: EditText
    private lateinit var mEditTextLiterPrice: EditText

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

    override fun setHistoryInformation(history: HistoryModel, attributesHistory: MutableList<AttributesHistoryModel>) {
        super.setHistoryInformation(history, attributesHistory)

        for (i in 0 until mAttributesViews.size) {
            val currentAttributeHistory: AttributesHistoryModel = attributesHistory[i]
            val currentAttributeView = mAttributesViews[i]
            currentAttributeView.setText(currentAttributeHistory.value)
        }
    }

    override fun saveHistory(cost: CostTypeModel, date: Date, odometer: Double, comment: String, price: Double) {
        mPresenter.saveFuelHistory(
                cost, date, odometer, comment, price,
                mAttributesViews)
    }

    override fun updateHistory(date: Date, odometer: Double, comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>) {
        mPresenter.updateFuelHistory(
                date, odometer, comment, price,
                mAttributesViews, attributesHistory)
    }

    override fun getLayoutResId(): Int = R.layout.activity_fuel_details

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mEditTextFuelType = findViewById(R.id.edit_text_fuel_type) as EditText
        mEditTextLiters = findViewById(R.id.edit_text_liters) as EditText
        mEditTextLiterPrice = findViewById(R.id.edit_text_price_per_liter) as EditText

        mAttributesViews.add(mEditTextFuelType)
        mAttributesViews.add(mEditTextLiters)
        mAttributesViews.add(mEditTextLiterPrice)
    }
}