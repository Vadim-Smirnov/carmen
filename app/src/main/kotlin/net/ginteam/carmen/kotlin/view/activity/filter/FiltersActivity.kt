package net.ginteam.carmen.kotlin.view.activity.filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.FiltersContract
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.FilterModel
import net.ginteam.carmen.kotlin.presenter.filter.FiltersPresenter
import net.ginteam.carmen.kotlin.view.activity.BaseActivity
import net.ginteam.carmen.view.custom.FilterEditText
import java.util.*

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */
class FiltersActivity : BaseActivity <FiltersContract.View, FiltersContract.Presenter>(), FiltersContract.View, FilterEditText.OnFilterChangeListener {

    override var mPresenter: FiltersContract.Presenter = FiltersPresenter()

    private lateinit var mSelectedCategory: CategoryModel
    private var mFilterQuery: String = ""

    private lateinit var mFiltersFields: MutableList <FilterEditText>

    private lateinit var mLayoutFiltersFields: LinearLayout
    private lateinit var mTextViewResultsCount: TextView
    private lateinit var mProgressBarCounter: ProgressBar

    companion object {
        const val CATEGORY_ARGUMENT = "category"
        const val FILTER_CONFIRM_REQUEST_CODE = 101
        const val RESULT_FILTER_ARGUMENT = "result_filter"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.fetchFilters(mSelectedCategory)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filters_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_item_reset_filters -> resetFiltersFields()
        }
        return true
    }

    override fun onFilterChanged(filterEditText: FilterEditText?, editable: Editable?) {
        mFilterQuery = getFilterQueryWithFields()
        mPresenter.updateResultsCount(mSelectedCategory, mFilterQuery)
    }

    override fun getLayoutResId(): Int = R.layout.activity_filter

    override fun getCurrentViewState(): FiltersContract.ViewState {
        return object : FiltersContract.ViewState {
            override var categoryId: Int = mSelectedCategory.id
            override var filterQuery: String = mFilterQuery
            override var fieldsList: List<FilterEditText> = mFiltersFields
        }
    }

    override fun showCounterLoading(show: Boolean) {
        mProgressBarCounter.visibility = View.VISIBLE
        mTextViewResultsCount.visibility = View.GONE
    }

    override fun updateFilterResultsCount(count: Int) {
        mProgressBarCounter.visibility = View.GONE
        mTextViewResultsCount.visibility = View.VISIBLE
        mTextViewResultsCount.text = if (count != 0) {
            findViewById(R.id.text_view_show_results).visibility = View.VISIBLE
            String.format(getString(R.string.results_count_string), count)
        } else {
            findViewById(R.id.text_view_show_results).visibility = View.INVISIBLE
            getString(R.string.no_filters_result_string)
        }
    }

    override fun showFilters(filters: List<FilterModel>) {
        mFiltersFields = ArrayList<FilterEditText>()
        for (currentFilter in filters) {
            val currentField = FilterEditText(getContext())
            currentField.setFilterModel(currentFilter)
            currentField.setOnFilterChangeListener(this)

            mFiltersFields.add(currentField)
            mLayoutFiltersFields.addView(currentField)
        }
        updateFiltersDependencies()
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        setToolbarTitle(getString(R.string.filter_activity_title))
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mLayoutFiltersFields = findViewById(R.id.layout_filters) as LinearLayout
        mTextViewResultsCount = findViewById(R.id.text_view_filters_result_count) as TextView
        mProgressBarCounter = findViewById(R.id.progress_bar_filter) as ProgressBar

        findViewById(R.id.text_view_show_results).setOnClickListener { confirmFilters() }
    }

    override fun updateDependencies() {
        super.updateDependencies()
        mSelectedCategory = intent.getSerializableExtra(CATEGORY_ARGUMENT) as CategoryModel
    }

    private fun updateFiltersDependencies() {
        val restoredViewState: FiltersContract.ViewState? = mPresenter.tryToRestoreViewState(mSelectedCategory)
        restoredViewState?.let {
            mFilterQuery = it.filterQuery
            it.restoreFieldsState(mFiltersFields)
        }
        mPresenter.updateResultsCount(mSelectedCategory, mFilterQuery)
    }

    private fun confirmFilters() {
        mPresenter.saveCurrentViewState()

        val intent = Intent()
        intent.putExtra(RESULT_FILTER_ARGUMENT, mFilterQuery)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getFilterQueryWithFields(): String {
        var resultFilter = ""
        for (currentFilter in mFiltersFields) {
            resultFilter += currentFilter.filterQuery
        }
        return resultFilter
    }

    private fun resetFiltersFields() {
        for (currentField in mFiltersFields) {
            currentField.resetFilter()
        }
    }
}