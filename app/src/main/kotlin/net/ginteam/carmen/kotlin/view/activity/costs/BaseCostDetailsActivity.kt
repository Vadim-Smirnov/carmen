package net.ginteam.carmen.kotlin.view.activity.costs

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Order
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseCostDetailsActivityContract
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.view.activity.BaseActivity
import net.ginteam.carmen.view.custom.FilterEditText
import net.ginteam.carmen.view.custom.NumberPicker
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


abstract class BaseCostDetailsActivity<in V : BaseCostDetailsActivityContract.View, T : BaseCostDetailsActivityContract.Presenter <V>>
    : BaseActivity <V, T>(), BaseCostDetailsActivityContract.View, DatePickerDialog.OnDateSetListener,
        Validator.ValidationListener, FilterEditText.OnFilterChangeListener {

    protected var mCostId: Long = -1
    protected var mHistoryId: Long = -1

    protected lateinit var mFetchedCost: CostTypeModel
    protected var mUpdatableAttributes: MutableList <AttributesHistoryModel>? = null

    @Order(1)
    @NotEmpty(messageResId = R.string.cost_details_date_wrong)
    protected var mEditTextDate: EditText? = null
    @Order(7)
    @NotEmpty(messageResId = R.string.cost_details_price_wrong)
    protected var mEditTextPrice: EditText? = null

    protected var mFilterEditTextDate: FilterEditText? = null
    protected var mLinearLayoutOdometer: LinearLayout? = null
    protected var mFilterEditTextComment: FilterEditText? = null
    protected var mFilterEditTextPrice: FilterEditText? = null

    protected var mSelectedDate: Calendar? = null

    protected var mHistoryModel: HistoryModel? = null

    protected lateinit var mFieldsValidator: Validator

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
        val colorIcon = Color.parseColor(cost.color)
        mToolbar?.setBackgroundColor(colorIcon)
        setToolbarTitle(cost.name)
        ContextCompat.getDrawable(this, R.drawable.ic_calendar_white).setColorFilter(colorIcon, PorterDuff.Mode.MULTIPLY)
        ContextCompat.getDrawable(this, R.drawable.ic_coins_white).setColorFilter(colorIcon, PorterDuff.Mode.MULTIPLY)
        ContextCompat.getDrawable(this, R.drawable.ic_refresh_white).setColorFilter(colorIcon, PorterDuff.Mode.MULTIPLY)
        ContextCompat.getDrawable(this, R.drawable.ic_comment_white).setColorFilter(colorIcon, PorterDuff.Mode.MULTIPLY)
        ContextCompat.getDrawable(this, R.drawable.ic_pricetag_white).setColorFilter(colorIcon, PorterDuff.Mode.MULTIPLY)
        ContextCompat.getDrawable(this, R.drawable.ic_timer_white).setColorFilter(colorIcon, PorterDuff.Mode.MULTIPLY)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let { menuInflater.inflate(R.menu.cost_details_menu, menu) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        val firstError = errors?.get(0)
        showError(firstError?.getCollatedErrorMessage(getContext()))
        firstError?.view?.requestFocus()
    }

    override fun onValidationSucceeded() {
        var odometer: String = ""
                for (i in 0..mLinearLayoutOdometer!!.childCount - 1 step 1) {
                    odometer += (mLinearLayoutOdometer!!.getChildAt(i) as NumberPicker).current
                }
        SharedPreferencesManager.odometer = odometer
                saveHistoryOrUpdate(
                        mFetchedCost,
                        mSelectedDate!!.time,
                        odometer.toInt(),
                        mFilterEditTextComment?.text.toString(),
                        "${mFilterEditTextPrice?.text}".toDouble(),
                        mUpdatableAttributes
                )
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_save -> {
                mFieldsValidator.validate()
            }
            else -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setHistoryInformation(history: HistoryModel, attributesHistory: MutableList <AttributesHistoryModel>) {
        mUpdatableAttributes = attributesHistory
        mSelectedDate!!.time = history.date
        mHistoryModel = history
        mFilterEditTextDate?.text = SimpleDateFormat("dd MMMMMM").format(mSelectedDate!!.time)

        setOdometer(history.odometer.toString())

        mFilterEditTextComment?.text = history.comment
        mFilterEditTextPrice?.text = history.price.toString()
    }

    override fun setHistoryInformation(history: HistoryModel) {
        mHistoryModel = history
        mSelectedDate!!.time = history.date

        mFilterEditTextDate?.text = SimpleDateFormat("dd MMMMMM").format(mSelectedDate!!.time)

        setOdometer(history.odometer.toString())
        val historyOdometer: String = history.odometer.toString()

        for (i in 0..historyOdometer.length - 1 step 1) {
            (mLinearLayoutOdometer!!.getChildAt(i + mLinearLayoutOdometer!!.childCount - historyOdometer.length)
                    as NumberPicker).current = historyOdometer[i].toString().toInt()
        }
        mFilterEditTextComment?.text = history.comment
        mFilterEditTextPrice?.text = history.price.toString()
    }

    override fun close() {
        finish()
    }

    override fun updateDependencies() {
        super.updateDependencies()

        mCostId = intent.getLongExtra(COST_ID_ARGUMENT, -1)
        mHistoryId = intent.getLongExtra(HISTORY_ID_ARGUMENT, -1)

        mAttributesViews = ArrayList <FilterEditText>()

        mFieldsValidator = Validator(this)
        mFieldsValidator.setValidationListener(this)
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_button)

        mFilterEditTextDate = findViewById(R.id.filter_edit_text_date) as FilterEditText?
        mEditTextDate = mFilterEditTextDate!!.editTextFilter
        mLinearLayoutOdometer = findViewById(R.id.odometer) as LinearLayout?
        mFilterEditTextComment = findViewById(R.id.filter_edit_text_comment) as FilterEditText?
        mFilterEditTextPrice = findViewById(R.id.filter_edit_text_price) as FilterEditText?
        mEditTextPrice = mFilterEditTextPrice!!.editTextFilter
        mFilterEditTextPrice!!.editTextFilter.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
        mFilterEditTextPrice!!.setOnFilterChangeListener(this)

        mFilterEditTextDate!!.setOnFilterClickListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    showDatePickerDialog()
                    v.requestFocus()
                }
            }
            false
        }

        mSelectedDate = Calendar.getInstance()
        mFilterEditTextDate?.text = SimpleDateFormat("dd MMMMMM").format(mSelectedDate!!.time)

        val odometer = SharedPreferencesManager.odometer
        if (!odometer.isEmpty()) {
            setOdometer(odometer)
        }


    }

    protected abstract fun saveHistory(cost: CostTypeModel, date: Date, odometer: Int,
                                       comment: String, price: Double)

    protected abstract fun updateHistory(date: Date, odometer: Int,
                                         comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>? = null,
                                         history: HistoryModel? = null)

    protected fun fetchInformation() {
        if (mCostId != -1L) {
            mPresenter.fetchCostById(mCostId)
            return
        }

        mPresenter.fetchHistoryById(mHistoryId)
    }

    private fun saveHistoryOrUpdate(cost: CostTypeModel, date: Date, odometer: Int,
                                    comment: String, price: Double, attributesHistory: MutableList<AttributesHistoryModel>? = null) {
        if (mHistoryId != -1L) {
            updateHistory(date, odometer, comment, price, attributesHistory, mHistoryModel)
            return
        }
        saveHistory(cost, date, odometer, comment, price)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mSelectedDate!!.set(Calendar.YEAR, year)
        mSelectedDate!!.set(Calendar.MONTH, month)
        mSelectedDate!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        mFilterEditTextDate?.text = SimpleDateFormat("dd MMMMMM").format(mSelectedDate!!.time)
    }

    private fun showDatePickerDialog() {
        val dialog: DatePickerDialog = DatePickerDialog(this, this,
                mSelectedDate!!.get(Calendar.YEAR),
                mSelectedDate!!.get(Calendar.MONTH),
                mSelectedDate!!.get(Calendar.DAY_OF_MONTH))
        dialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        dialog.show()
    }

    override fun onFilterChanged(filterEditText: FilterEditText?, editable: Editable?) {
        val text = editable.toString()
        val length = text.length
        val PATTERN = "^[0-9]+\\.?[0-9]*$"
        if (length > 0 && !Pattern.matches(PATTERN, text)) {
            editable!!.delete(length - 1, length)
        }
    }

    private fun setOdometer(historyOdometer: String) {
        for (i in 0..historyOdometer.length - 1 step 1) {
            (mLinearLayoutOdometer!!.getChildAt(i + mLinearLayoutOdometer!!.childCount - historyOdometer.length)
                    as NumberPicker).current = historyOdometer[i].toString().toInt()
        }
    }

}
