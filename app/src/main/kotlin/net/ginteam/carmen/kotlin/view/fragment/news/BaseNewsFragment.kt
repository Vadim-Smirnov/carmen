package net.ginteam.carmen.kotlin.view.fragment.news

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.view.adapter.news.BaseNewsAdapter
import net.ginteam.carmen.kotlin.view.fragment.BaseFragment

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

abstract class BaseNewsFragment <E : BaseNewsAdapter, in V : BaseNewsContract.View,
        T : BaseNewsContract.Presenter <V>> : BaseFragment <V, T>(), BaseNewsContract.View,
        (NewsModel) -> Unit, (NewsModel, Boolean) -> Unit {

    protected var mNewsItemSelectedListener: OnNewsItemSelectedListener? = null

    protected lateinit var mLayoutManager: LinearLayoutManager
    protected lateinit var mRecyclerViewNews: RecyclerView
    protected abstract var mNewsAdapter: E

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mNewsItemSelectedListener = context as OnNewsItemSelectedListener?
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchNews()
    }

    override fun getNetworkErrorAction(): (() -> Unit)? = {
        fetchNews()
    }

    /**
     * onNewsItemClick implementation for {@link BaseNewsAdapter}
     */
    override fun invoke(newsItem: NewsModel) {
        mNewsItemSelectedListener?.onNewsItemSelected(newsItem)
    }

    /**
     * onAddToFavoriteClick implementation for {@link BaseNewsAdapter}
     */
    override fun invoke(newsItem: NewsModel, isFavorite: Boolean) {
        if (isFavorite) {
            mPresenter.addNewsItemToFavorites(newsItem)
        } else {
            mPresenter.removeNewsItemFromFavorites(newsItem)
        }
    }

    override fun showFavoriteConfirmationMessage(newsItem: NewsModel, messageResId: Int) {
        mNewsAdapter.invalidateNewsItem(newsItem)

        Snackbar.make(activity.findViewById(R.id.main_fragment_container) ?: mFragmentView,
                getString(messageResId), Snackbar.LENGTH_LONG).show()
    }

    override fun showFavoriteErrorMessage(messageResId: Int) {
        Snackbar.make(activity.findViewById(R.id.main_fragment_container) ?: mFragmentView,
                getString(messageResId), Snackbar.LENGTH_LONG).show()
    }

    protected abstract fun fetchNews()

    protected abstract fun getRecyclerViewItemDecorator(): RecyclerView.ItemDecoration
    protected abstract fun getRecyclerViewLayoutManager(): LinearLayoutManager

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mRecyclerViewNews = mFragmentView.findViewById(R.id.recycler_view_news) as RecyclerView
        mRecyclerViewNews.addItemDecoration(getRecyclerViewItemDecorator())
        mLayoutManager = getRecyclerViewLayoutManager()
        mRecyclerViewNews.layoutManager = mLayoutManager
    }

    interface OnNewsItemSelectedListener {

        fun onNewsItemSelected(newsItem: NewsModel)

    }

}