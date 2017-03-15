package net.ginteam.carmen.kotlin.view.adapter.news

import net.ginteam.carmen.kotlin.model.NewsModel

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

open class FavoritesNewsAdapter(news: MutableList <NewsModel>,
                                onNewsItemClick: (NewsModel) -> Unit,
                                onFavoriteClick: (NewsModel, Boolean) -> Unit)
    : VerticalNewsAdapter(news, onNewsItemClick, onFavoriteClick) {

    private var mLastRemovedPosition: Int? = null
    private var mLastRemovedNewsItem: NewsModel? = null

    fun removeNewsItem(newsItem: NewsModel) {
        mLastRemovedNewsItem = news.find { newsItem.id == it.id }!!
        mLastRemovedPosition = news.indexOf(mLastRemovedNewsItem!!)

        news.removeAt(mLastRemovedPosition!!)
        notifyItemRemoved(mLastRemovedPosition!!)
    }

    fun restoreRemovedNewsItem() {
        news.add(mLastRemovedPosition!!, mLastRemovedNewsItem!!)
        notifyItemInserted(mLastRemovedPosition!!)
    }

}