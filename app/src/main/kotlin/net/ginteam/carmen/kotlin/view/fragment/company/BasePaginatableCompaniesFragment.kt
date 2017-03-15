package net.ginteam.carmen.kotlin.view.fragment.news

import android.os.Handler
import net.ginteam.carmen.kotlin.contract.BaseCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.view.adapter.company.PaginatableCompaniesAdapter
import net.ginteam.carmen.kotlin.view.fragment.company.BaseCompaniesFragment
import net.ginteam.carmen.view.adapter.company.PaginationScrollListener

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

abstract class BasePaginatableCompaniesFragment <E : PaginatableCompaniesAdapter, in V : BaseCompaniesContract.View,
        T : BaseCompaniesContract.Presenter <V>> : BaseCompaniesFragment<E, V, T>() {

    protected val mUiThreadHandler: Handler = Handler()

    protected var isLoadingNow: Boolean = false
    protected var mCurrentPaginationPage: Int = 1

    override lateinit var mCompaniesAdapter: E

    override fun showCompanies(companies: MutableList<CompanyModel>, pagination: PaginationModel?) {
        if (pagination != null) {
            mCompaniesAdapter = initializeAdapter(companies, this, this)
            mRecyclerViewCompanies.adapter = mCompaniesAdapter
            mRecyclerViewCompanies.setOnScrollListener(initializePaginationScrollListener(pagination))
            return
        }

        isLoadingNow = false
        mUiThreadHandler.post {
            mCompaniesAdapter.hideLoading()
            mCompaniesAdapter.addCompanies(companies)
        }
    }

    protected abstract fun initializeAdapter(news: MutableList<CompanyModel>,
                                             onItemClick: (CompanyModel) -> Unit,
                                             onFavoriteClick: (CompanyModel, Boolean) -> Unit) : E

    private fun initializePaginationScrollListener(paginationDetails: PaginationModel): PaginationScrollListener {
        return object : PaginationScrollListener(mLayoutManager) {
            override fun loadMoreItems() {
                mCurrentPaginationPage++
                isLoadingNow = true
                mUiThreadHandler.post { mCompaniesAdapter.showLoading() }
                fetchCompanies()
            }

            override fun isLastPage(): Boolean = mCurrentPaginationPage == paginationDetails.totalPages
            override fun isLoading(): Boolean = isLoadingNow
        }
    }

}