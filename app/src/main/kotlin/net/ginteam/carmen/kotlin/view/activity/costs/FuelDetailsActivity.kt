package net.ginteam.carmen.kotlin.view.activity.costs

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.widget.EditText
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Order
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.FuelDetailsActivityContract
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeAttributeModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.presenter.costs.FuelDetailsActivityPresenter
import net.ginteam.carmen.model.filter.FilterModel
import net.ginteam.carmen.view.custom.FilterEditText
import java.util.*
import java.util.regex.Pattern


/**
 * Created by eugene_shcherbinock on 3/21/17.
 */

class FuelDetailsActivity : BaseCostDetailsActivity <FuelDetailsActivityContract.View, FuelDetailsActivityContract.Presenter>(),
        FuelDetailsActivityContract.View, FilterEditText.OnFilterChangeListener {

    override var mPresenter: FuelDetailsActivityContract.Presenter = FuelDetailsActivityPresenter()

    private lateinit var mFilterEditTextFuelType: FilterEditText
    private lateinit var mFilterEditTextLiters: FilterEditText
    private lateinit var mFilterEditTextLiterPrice: FilterEditText

    @Order(2)
    @NotEmpty(messageResId = R.string.cost_details_fuel_type_wrong)
    private lateinit var mEditTextFuelType: EditText
    @Order(3)
    @NotEmpty(messageResId = R.string.cost_details_liters_wrong)
    private lateinit var mEditTextLiters: EditText
    @Order(4)
    @NotEmpty(messageResId = R.string.cost_details_liter_price_wrong)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.fetchFuelType()
    }

    override fun setHistoryInformation(history: HistoryModel, attributesHistory: MutableList<AttributesHistoryModel>) {
        super.setHistoryInformation(history, attributesHistory)

        for (i in 0 until mAttributesViews.size) {
            val currentAttributeHistory: AttributesHistoryModel = attributesHistory[i]
            val currentAttributeView = mAttributesViews[i]
            currentAttributeView.text = currentAttributeHistory.value
        }
    }

    override fun saveHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double) {
        mPresenter.saveFuelHistory(
                cost, date, odometer, comment, price,
                mAttributesViews)
    }

    override fun updateHistory(date: Date, odometer: Int, comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>?, history: HistoryModel?) {
        mPresenter.updateFuelHistory(
                date, odometer, comment, price,
                mAttributesViews, attributesHistory!!)
    }

    override fun getLayoutResId(): Int = R.layout.activity_fuel_details

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mFilterEditTextFuelType = findViewById(R.id.filter_edit_text_fuel_type) as FilterEditText
        mEditTextFuelType = mFilterEditTextFuelType.editTextFilter
        mFilterEditTextLiters = findViewById(R.id.filter_edit_text_liters) as FilterEditText
        mEditTextLiters = mFilterEditTextLiters.editTextFilter
        mFilterEditTextLiterPrice = findViewById(R.id.filter_edit_text_liter_price) as FilterEditText
        mEditTextLiterPrice = mFilterEditTextLiterPrice.editTextFilter

        mFilterEditTextLiters.editTextFilter.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
        mFilterEditTextLiterPrice.editTextFilter.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL

        mFilterEditTextPrice!!.setOnFilterChangeListener(this)
        mFilterEditTextLiters.setOnFilterChangeListener(this)
        mFilterEditTextLiterPrice.setOnFilterChangeListener(this)

        mAttributesViews.add(mFilterEditTextFuelType)
        mAttributesViews.add(mFilterEditTextLiters)
        mAttributesViews.add(mFilterEditTextLiterPrice)
    }

    override fun showFuelType(fuelTypes: FilterModel) {
        mFilterEditTextFuelType.setFilterModel(fuelTypes)
    }

    override fun onFilterChanged(filterEditText: FilterEditText?, editable: Editable?) {
        val text = editable.toString()
        val length = text.length
        val PATTERN = "^[0-9]+\\.?[0-9]*$"
        if (length > 0 && !Pattern.matches(PATTERN, text)) {
            editable!!.delete(length - 1, length)
        }
        val liters = mFilterEditTextLiters.text
        val literPrice = mFilterEditTextLiterPrice.text
        val price = mFilterEditTextPrice!!.text

        when (filterEditText!!.id) {
            R.id.filter_edit_text_price -> {
                if (!price.isEmpty() && !literPrice.isEmpty()) {
                    mFilterEditTextLiters.setOnFilterChangeListener(null)
                    mFilterEditTextLiters.text = (price.toDouble() / literPrice.toDouble()).toString()
                    mFilterEditTextLiters.setOnFilterChangeListener(this)
                }
            }
            else -> {

                if (!liters.isEmpty() && !literPrice.isEmpty()) {
                    mFilterEditTextPrice!!.setOnFilterChangeListener(null)
                    mFilterEditTextPrice!!.text = (liters.toDouble() * literPrice.toDouble()).toString()
                    mFilterEditTextPrice!!.setOnFilterChangeListener(this)

                } else {
                    mFilterEditTextPrice!!.text = ""
                }
            }
        }
    }
}