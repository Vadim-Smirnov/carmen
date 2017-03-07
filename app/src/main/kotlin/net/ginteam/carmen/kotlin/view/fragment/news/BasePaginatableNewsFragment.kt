package net.ginteam.carmen.kotlin.view.fragment.news

import android.os.Handler
import net.ginteam.carmen.kotlin.contract.BaseNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.view.adapter.news.PaginatableNewsAdapter
import net.ginteam.carmen.view.adapter.company.PaginationScrollListener

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

abstract class BasePaginatableNewsFragment <E : PaginatableNewsAdapter, in V : BaseNewsContract.View,
        T : BaseNewsContract.Presenter <V>> : BaseNewsFragment<E, V, T>() {

    protected val mUiThreadHandler: Handler = Handler()

    protected var isLoadingNow: Boolean = false
    protected var mCurrentPaginationPage: Int = 1

    override fun showNews(news: MutableList<NewsModel>, pagination: PaginationModel?) {
        if (pagination != null) {
            mNewsAdapter = initializeAdapter(news, this, this)
            mRecyclerViewNews.adapter = mNewsAdapter
            mRecyclerViewNews.setOnScrollListener(initializePaginationScrollListener(pagination))
            return
        }

        isLoadingNow = false
        mUiThreadHandler.post {
            mNewsAdapter.hideLoading()
            mNewsAdapter.addNews(news)
        }
    }

    protected abstract fun initializeAdapter(news: MutableList<NewsModel>,
                                             onItemClick: (NewsModel) -> Unit,
                                             onFavoriteClick: (NewsModel, Boolean) -> Unit) : E

    private fun initializePaginationScrollListener(paginationDetails: PaginationModel): PaginationScrollListener {
        return object : PaginationScrollListener(mLayoutManager) {
            override fun loadMoreItems() {
                mCurrentPaginationPage++
                isLoadingNow = true
                mUiThreadHandler.post { mNewsAdapter.showLoading() }
                fetchNews()
            }

            override fun isLastPage(): Boolean = mCurrentPaginationPage == paginationDetails.totalPages
            override fun isLoading(): Boolean = isLoadingNow
        }
    }

}