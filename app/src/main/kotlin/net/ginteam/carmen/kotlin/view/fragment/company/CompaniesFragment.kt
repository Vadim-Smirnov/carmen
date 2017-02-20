package net.ginteam.carmen.kotlin.view.fragment.company

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ImageView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.CompaniesContract
import net.ginteam.carmen.kotlin.interfaces.Filterable
import net.ginteam.carmen.kotlin.interfaces.Sortable
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.presenter.company.CompaniesPresenter
import net.ginteam.carmen.kotlin.view.adapter.company.PaginatableCompaniesAdapter
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListVerticalItemDecorator
import net.ginteam.carmen.view.adapter.company.PaginationScrollListener

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

class CompaniesFragment
    : BaseCompaniesFragment<PaginatableCompaniesAdapter, CompaniesContract.View, CompaniesContract.Presenter>(),
        CompaniesContract.View, Filterable, Sortable {

    override var mPresenter: CompaniesContract.Presenter = CompaniesPresenter()

    private val mAdapterNotifyHandler: Handler = Handler()
    override lateinit var mCompaniesAdapter: PaginatableCompaniesAdapter

    private lateinit var mSearchView: SearchView

    private var mFilterQuery: String = ""

    // set default sort options
    private var mSortField: String = "rating"
    private var mSortType: String = "desc"

    private lateinit var mSelectedCategory: CategoryModel
    private var isLoadingNow: Boolean = false
    private var mCurrentPaginationPage: Int = 1
    private var mMenuItemSelectedListener: OnBottomMenuItemSelectedListener? = null

    companion object {
        private const val SEARCH_DELAY_MILLISECONDS: Long = 500
        private const val SEARCH_BUTTON_ID: Int = android.support.v7.appcompat.R.id.search_button

        const val CATEGORY_ARGUMENT = "category"

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSelectedCategory = arguments.getSerializable(CATEGORY_ARGUMENT) as CategoryModel
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.company_list_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        menu?.let {
            prepareSearchView(it)
        }
    }

    // Filterable & Sortable implementation

    override fun setFilterQuery(filter: String) {
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

    override fun getLayoutResId(): Int = R.layout.fragment_company_list

    override fun showCompanies(companies: MutableList<CompanyModel>, pagination: PaginationModel?) {
        if (pagination != null) {
            mCompaniesAdapter = PaginatableCompaniesAdapter(companies, this, this)
            mRecyclerViewCompanies.adapter = mCompaniesAdapter
            mRecyclerViewCompanies.setOnScrollListener(initializePaginationScrollListener(pagination))
            return
        }

        isLoadingNow = false
        mAdapterNotifyHandler.post {
            mCompaniesAdapter.hideLoading()
            mCompaniesAdapter.addCompanies(companies)
        }
    }

    override fun fetchCompanies() {
        mPresenter.fetchCompanies(mSelectedCategory.id, mFilterQuery, mSortField, mSortType, mCurrentPaginationPage)
    }

    override fun getRecyclerViewItemDecorator(): RecyclerView.ItemDecoration
            = CompanyRecyclerListVerticalItemDecorator(context, R.dimen.company_item_spacing)

    override fun getRecyclerViewLayoutManager(): LinearLayoutManager
            = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        setHasOptionsMenu(true)

        val bottomMenuLayout = mFragmentView.findViewById(R.id.bottom_navigation_layout)
        bottomMenuLayout.visibility = View.VISIBLE
        mFragmentView.findViewById(R.id.bottom_nav_item_categories).setOnClickListener {
            mMenuItemSelectedListener?.onShowCategoriesDialog()
        }
        mFragmentView.findViewById(R.id.bottom_nav_item_filters).setOnClickListener {
            mMenuItemSelectedListener?.onShowFiltersActivity(mSelectedCategory)
        }
        mFragmentView.findViewById(R.id.bottom_nav_item_sort).setOnClickListener {
            mMenuItemSelectedListener?.onShowSortDialog(mSelectedCategory)
        }

        val floatButton = mFragmentView.findViewById(R.id.float_button_show_map)
        floatButton.visibility = View.VISIBLE
        floatButton.setOnClickListener { mMenuItemSelectedListener?.onShowMap(mSelectedCategory) }
    }

    private fun initializePaginationScrollListener(paginationDetails: PaginationModel): PaginationScrollListener {
        return object : PaginationScrollListener(mLayoutManager) {
            override fun loadMoreItems() {
                mCurrentPaginationPage++
                isLoadingNow = true
                mAdapterNotifyHandler.post {
                    mCompaniesAdapter.showLoading()
                }
                fetchCompanies()
            }

            override fun isLastPage(): Boolean = mCurrentPaginationPage == paginationDetails.totalPages
            override fun isLoading(): Boolean = isLoadingNow
        }
    }

    private fun prepareSearchView(menu: Menu) {
        mSearchView = menu.findItem(R.id.action_search).actionView as SearchView

        // set search icon
        (mSearchView.findViewById(SEARCH_BUTTON_ID) as ImageView).setImageResource(R.drawable.ic_search)

        // create observer for search
//        Observable.create(Observable.OnSubscribe <String> { subscriber ->
//            mSearchView.setOnQueryTextListener(object : SearchViewListener() {
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    subscriber.onNext(newText)
//                    return true
//                }
//            })
//        })
//        .debounce(SEARCH_DELAY_MILLISECONDS, TimeUnit.MILLISECONDS)
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe({
//
//        })
    }

    interface OnBottomMenuItemSelectedListener {

        fun onShowCategoriesDialog()
        fun onShowMap(category: CategoryModel)
        fun onShowFiltersActivity(category: CategoryModel)
        fun onShowSortDialog(category: CategoryModel)

    }

}