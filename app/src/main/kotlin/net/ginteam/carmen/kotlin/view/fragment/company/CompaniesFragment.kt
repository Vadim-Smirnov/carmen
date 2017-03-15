package net.ginteam.carmen.kotlin.view.fragment.company

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ImageView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.Constants
import net.ginteam.carmen.kotlin.contract.CompaniesContract
import net.ginteam.carmen.kotlin.interfaces.Filterable
import net.ginteam.carmen.kotlin.interfaces.Sortable
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.presenter.company.list.CompaniesPresenter
import net.ginteam.carmen.kotlin.view.adapter.company.VerticalCompaniesAdapter

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

class CompaniesFragment : BaseVerticalCompaniesFragment<VerticalCompaniesAdapter,
        CompaniesContract.View, CompaniesContract.Presenter>(),
        CompaniesContract.View, Filterable, Sortable {

    override var mPresenter: CompaniesContract.Presenter = CompaniesPresenter()


    private var isNeedProceedSearchChanges: Boolean = false
    private lateinit var mSearchView: SearchView

    // set default sort options
    private var mFilterQuery: String = ""
    private var mSortField: String = "rating"
    private var mSortType: String = "desc"

    private lateinit var mSelectedCategory: CategoryModel
    private var mMenuItemSelectedListener: OnBottomMenuItemSelectedListener? = null

    companion object {
        private const val CATEGORY_ARGUMENT = "category"

        fun newInstance(category: CategoryModel): CompaniesFragment {
            val bundle = Bundle()
            bundle.putSerializable(CATEGORY_ARGUMENT, category)

            val instance: CompaniesFragment = CompaniesFragment()
            instance.arguments = bundle
            return instance
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mMenuItemSelectedListener = context as OnBottomMenuItemSelectedListener?
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        menu?.let { prepareSearchView(it) }
    }

    // Filterable & Sortable implementation

    override fun setFilterQuery(filter: String) {
        isNeedProceedSearchChanges = false
        mSearchView.setQuery("", false)
        mSearchView.isIconified = true

        mFilterQuery = filter
        mCurrentPaginationPage = 1
        fetchCompanies()
    }

    override fun setSortQuery(field: String, sort: String) {
        mSortField = field
        mSortType = sort
        mCurrentPaginationPage = 1
        fetchCompanies()
    }

    // ------------------------------------

    override fun fetchCompanies() {
        mPresenter.fetchCompanies(mSelectedCategory.id, mFilterQuery, mSortField, mSortType, mCurrentPaginationPage)
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        setHasOptionsMenu(true)

        val bottomMenu = mFragmentView.findViewById(R.id.bottom_navigation_layout)
        bottomMenu.visibility = View.VISIBLE
        bottomMenu.findViewById(R.id.bottom_nav_item_categories).setOnClickListener {
            mMenuItemSelectedListener?.onShowCategoriesDialog()
        }
        bottomMenu.findViewById(R.id.bottom_nav_item_filters).setOnClickListener {
            mMenuItemSelectedListener?.onShowFiltersActivity(mSelectedCategory)
        }
        bottomMenu.findViewById(R.id.bottom_nav_item_sort).setOnClickListener {
            mMenuItemSelectedListener?.onShowSortDialog(mSelectedCategory)
        }

        val showMapButton = mFragmentView.findViewById(R.id.float_button_show_map)
        showMapButton.visibility = View.VISIBLE
        showMapButton.setOnClickListener { mMenuItemSelectedListener?.onShowMap(mSelectedCategory) }
    }

    override fun updateDependencies() {
        super.updateDependencies()
        mSelectedCategory = arguments.getSerializable(CATEGORY_ARGUMENT) as CategoryModel
    }

    private fun prepareSearchView(menu: Menu) {
        mSearchView = menu.findItem(R.id.action_search).actionView as SearchView

        // set search icon
        (mSearchView.findViewById(Constants.SEARCH_BUTTON_ID) as ImageView).setImageResource(R.drawable.ic_search)

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (isNeedProceedSearchChanges && TextUtils.isEmpty(newText)) {
                    onQueryTextSubmit(newText)
                }
                isNeedProceedSearchChanges = true
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // reset filters
                mPresenter.resetFilters()
                mFilterQuery = "name:${query!!};"
                mCurrentPaginationPage = 1

                fetchCompanies()
                mUiThreadHandler.post { mSearchView.clearFocus() }

                return true
            }
        })
    }

    interface OnBottomMenuItemSelectedListener {

        fun onShowCategoriesDialog()
        fun onShowMap(category: CategoryModel)
        fun onShowFiltersActivity(category: CategoryModel)
        fun onShowSortDialog(category: CategoryModel)

    }

}