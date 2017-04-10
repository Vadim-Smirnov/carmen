package net.ginteam.carmen.kotlin.view.activity.costs

import android.util.Log
import android.widget.EditText
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Order
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.ServiceDetailsActivityContract
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeAttributeModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.presenter.costs.ServiceDetailsActivityPresenter
import net.ginteam.carmen.view.custom.FilterEditText
import java.util.*

/**
 * Created by vadimsmirnov on 05.04.17.
 */


class ServiceDetailsActivity : BaseCostDetailsActivity <ServiceDetailsActivityContract.View,
        ServiceDetailsActivityContract.Presenter>(), ServiceDetailsActivityContract.View {

    override var mPresenter: ServiceDetailsActivityContract.Presenter = ServiceDetailsActivityPresenter()

    private lateinit var mFilterEditTextServiceName: FilterEditText

    @Order(5)
    @NotEmpty(messageResId = R.string.cost_details_service_name_wrong)
    private lateinit var mEditTextServiceName: EditText

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
            currentAttributeView.text = currentAttributeHistory.value
        }
    }

    override fun saveHistory(cost: CostTypeModel, date: Date, odometer: Int, comment: String, price: Double) {
        mPresenter.saveServiceHistory(
                cost, date, odometer, comment, price,
                mAttributesViews)
    }

    override fun updateHistory(date: Date, odometer: Int, comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>?, history: HistoryModel?) {
        mPresenter.updateServiceHistory(
                date, odometer, comment, price,
                mAttributesViews, attributesHistory!!)
    }

    override fun getLayoutResId(): Int = R.layout.activity_service_details

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mFilterEditTextServiceName = findViewById(R.id.filter_edit_text_service_name) as FilterEditText
        mEditTextServiceName = mFilterEditTextServiceName.editTextFilter

        mAttributesViews.add(mFilterEditTextServiceName)
    }

}