package net.ginteam.carmen.kotlin.view.activity.costs

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.DatePicker
import android.widget.LinearLayout
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseCostDetailsActivityContract
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.view.activity.BaseActivity
import net.ginteam.carmen.view.custom.FilterEditText
import net.ginteam.carmen.view.custom.NumberPicker
import java.text.SimpleDateFormat
import java.util.*


abstract class BaseCostDetailsActivity<in V : BaseCostDetailsActivityContract.View, T : BaseCostDetailsActivityContract.Presenter <V>>
    : BaseActivity <V, T>(),
        BaseCostDetailsActivityContract.View, DatePickerDialog.OnDateSetListener {

    protected var mCostId: Long = -1
    protected var mHistoryId: Long = -1

    protected lateinit var mFetchedCost: CostTypeModel
    protected var mUpdatableAttributes: MutableList <AttributesHistoryModel>? = null

    protected var mEditTextDate: FilterEditText? = null
    protected var mLinearLayoutOdometer: LinearLayout? = null
    protected var mEditTextComment: FilterEditText? = null
    protected var mEditTextPrice: FilterEditText? = null

    protected var mSelectedDate: Calendar? = null

    protected lateinit var mAttributesViews: MutableList <FilterEditText>

    companion object {
        const val COST_ID_ARGUMENT = "cost_argument"
        const val HISTORY_ID_ARGUMENT = "history_argument"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchInformation()
    }

    override fun setCostInformation(cost: CostTypeModel) {
        mFetchedCost = cost
        mToolbar?.setBackgroundColor(Color.parseColor(cost.color))
        setToolbarTitle(cost.name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let { menuInflater.inflate(R.menu.cost_details_menu, menu) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_save -> {
                var odometer: String = ""
                for (i in 0..mLinearLayoutOdometer!!.childCount - 1 step 1) {
                    odometer += (mLinearLayoutOdometer!!.getChildAt(i) as NumberPicker).current
                }
                saveHistoryOrUpdate(
                        mFetchedCost,
                        mSelectedDate!!.time,
                        odometer.toInt(),
                        mEditTextComment?.text.toString(),
                        "${mEditTextPrice?.text}".toDouble(),
                        mUpdatableAttributes
                )
            }
            else -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setHistoryInformation(history: HistoryModel, attributesHistory: MutableList <AttributesHistoryModel>) {
        mUpdatableAttributes = attributesHistory
        mSelectedDate!!.time = history.date

        mEditTextDate?.text = SimpleDateFormat("dd MMMMMM").format(mSelectedDate!!.time)

        val historyOdometer: String = history.odometer.toString()

        for (i in 0..historyOdometer.length - 1 step 1) {
            (mLinearLayoutOdometer!!.getChildAt(i + mLinearLayoutOdometer!!.childCount - historyOdometer.length)
                    as NumberPicker).current = historyOdometer[i].toString().toInt()
        }
        mEditTextComment?.text = history.comment
        mEditTextPrice?.text = history.price.toString()
    }

    override fun setHistoryInformation(history: HistoryModel) {
        mSelectedDate!!.time = history.date

        mEditTextDate?.text = SimpleDateFormat("dd MMMMMM").format(mSelectedDate!!.time)

        val historyOdometer: String = history.odometer.toString()

        for (i in 0..historyOdometer.length - 1 step 1) {
            (mLinearLayoutOdometer!!.getChildAt(i + mLinearLayoutOdometer!!.childCount - historyOdometer.length)
                    as NumberPicker).current = historyOdometer[i].toString().toInt()
        }
        mEditTextComment?.text = history.comment
        mEditTextPrice?.text = history.price.toString()
    }

    override fun close() {
        finish()
    }

    override fun updateDependencies() {
        super.updateDependencies()

        mCostId = intent.getLongExtra(COST_ID_ARGUMENT, -1)
        mHistoryId = intent.getLongExtra(HISTORY_ID_ARGUMENT, -1)

        mAttributesViews = ArrayList <FilterEditText>()
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_button)

        mEditTextDate = findViewById(R.id.filter_edit_text_date) as FilterEditText?
        mLinearLayoutOdometer = findViewById(R.id.odometer) as LinearLayout?
        mEditTextComment = findViewById(R.id.filter_edit_text_comment) as FilterEditText?
        mEditTextPrice = findViewById(R.id.filter_edit_text_price) as FilterEditText?

        mEditTextDate!!.setOnFilterClickListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    showDatePickerDialog()
                    v.requestFocus()
                }
            }
            false
        }

        mSelectedDate = Calendar.getInstance()
        mEditTextDate?.text = SimpleDateFormat("dd MMMMMM").format(mSelectedDate!!.time)

    }

    protected abstract fun saveHistory(cost: CostTypeModel, date: Date, odometer: Int,
                                       comment: String, price: Double)

    protected abstract fun updateHistory(date: Date, odometer: Int,
                                         comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>)

    protected fun fetchInformation() {
        if (mCostId != -1L) {
            mPresenter.fetchCostById(mCostId)
            return
        }

        mPresenter.fetchHistoryById(mHistoryId)
    }

    private fun saveHistoryOrUpdate(cost: CostTypeModel, date: Date, odometer: Int,
                                    comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>? = null) {
        attributesHistory?.let {
            updateHistory(date, odometer, comment, price, it)
            return
        }
        saveHistory(cost, date, odometer, comment, price)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mSelectedDate!!.set(Calendar.YEAR, year)
        mSelectedDate!!.set(Calendar.MONTH, month)
        mSelectedDate!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        mEditTextDate?.text = SimpleDateFormat("dd MMMMMM").format(mSelectedDate!!.time)
    }

    private fun showDatePickerDialog() {
        val dialog: DatePickerDialog = DatePickerDialog(this, this,
                mSelectedDate!!.get(Calendar.YEAR),
                mSelectedDate!!.get(Calendar.MONTH),
                mSelectedDate!!.get(Calendar.DAY_OF_MONTH))
        dialog.show()
    }

}
