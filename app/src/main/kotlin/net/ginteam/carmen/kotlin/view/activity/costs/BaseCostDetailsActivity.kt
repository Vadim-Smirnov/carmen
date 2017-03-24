package net.ginteam.carmen.kotlin.view.activity.costs

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.MenuItem
import android.widget.EditText
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.CostDetailsActivityContract
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.view.activity.BaseActivity
import java.util.*

abstract class BaseCostDetailsActivity<in V : CostDetailsActivityContract.View, T : CostDetailsActivityContract.Presenter <V>>
    : BaseActivity <V, T>(),
        CostDetailsActivityContract.View {

    protected var mCostId: Long = -1
    protected var mHistoryId: Long = -1

    protected lateinit var mFetchedCost: CostTypeModel
    protected var mUpdatableAttributes: MutableList <AttributesHistoryModel>? = null

    protected var mEditTextDate: EditText? = null
    protected var mEditTextOdometer: EditText? = null
    protected var mEditTextComment: EditText? = null
    protected var mEditTextPrice: EditText? = null
    protected var mFloatButtonSave: FloatingActionButton? = null

    protected lateinit var mAttributesViews: MutableList <EditText>

    companion object {
        const val COST_ID_ARGUMENT = "cost_argument"
        const val HISTORY_ID_ARGUMENT = "history_argument"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchInformation()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun setCostInformation(cost: CostTypeModel) {
        mFetchedCost = cost
        mToolbar?.setBackgroundColor(Color.parseColor(cost.color))
        setToolbarTitle(cost.name)
    }

    override fun setHistoryInformation(history: HistoryModel, attributesHistory: MutableList <AttributesHistoryModel>) {
        mUpdatableAttributes = attributesHistory
        mEditTextDate?.setText(history.date.toString())
        mEditTextOdometer?.setText(history.odometer.toString())
        mEditTextComment?.setText(history.comment)
        mEditTextPrice?.setText(history.price.toString())
    }

    override fun close() {
        finish()
    }

    override fun updateDependencies() {
        super.updateDependencies()

        mCostId = intent.getLongExtra(COST_ID_ARGUMENT, -1)
        mHistoryId = intent.getLongExtra(HISTORY_ID_ARGUMENT, -1)

        mAttributesViews = ArrayList <EditText>()
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_button)

        mEditTextDate = findViewById(R.id.edit_text_date) as EditText?
        mEditTextOdometer = findViewById(R.id.edit_text_odometer) as EditText?
        mEditTextComment = findViewById(R.id.edit_text_comment) as EditText?
        mEditTextPrice = findViewById(R.id.edit_text_price) as EditText?

        mEditTextDate?.setText("${Date()}")

        mFloatButtonSave = findViewById(R.id.float_button_save_details) as FloatingActionButton?
        mFloatButtonSave?.setOnClickListener {
            saveHistoryOrUpdate(
                    mFetchedCost,
                    Date(mEditTextDate?.text.toString()),
                    "${mEditTextOdometer?.text}".toDouble(),
                    mEditTextComment?.text.toString(),
                    "${mEditTextPrice?.text}".toDouble(),
                    mUpdatableAttributes
            )
        }
    }

    protected abstract fun saveHistory(cost: CostTypeModel, date: Date, odometer: Double,
                                       comment: String, price: Double)

    protected abstract fun updateHistory(date: Date, odometer: Double,
                                         comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>)

    protected fun fetchInformation() {
        if (mCostId != -1L) {
            mPresenter.fetchCostById(mCostId)
            return
        }
        mPresenter.fetchHistoryById(mHistoryId)
    }

    private fun saveHistoryOrUpdate(cost: CostTypeModel, date: Date, odometer: Double,
                                    comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>? = null) {
        attributesHistory?.let {
            updateHistory(date, odometer, comment, price, it)
            return
        }
        saveHistory(cost, date, odometer, comment, price)
    }
}
