package net.ginteam.carmen.kotlin.view.adapter.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.view.adapter.LoadingViewHolder

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

abstract class PaginatableNewsAdapter(news: MutableList <NewsModel>,
                                      onNewsItemClick: (NewsModel) -> Unit,
                                      onFavoriteClick: (NewsModel, Boolean) -> Unit)
    : BaseNewsAdapter(news, onNewsItemClick, onFavoriteClick) {

    enum class ITEM_TYPE {
        COMPANY, LOADING
    }

    protected var isShowLoadingIndicator: Boolean = false

    fun addNews(news: MutableList<NewsModel>) {
        val insertPosition = this.news.size
        this.news.addAll(news)
        notifyItemRangeInserted(insertPosition, news.size)
    }

    fun showLoading() {
        isShowLoadingIndicator = true
        news.add(NewsModel())
        notifyItemInserted(news.size - 1)
    }

    fun hideLoading() {
        isShowLoadingIndicator = false
        val position = news.size - 1
        news.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE.COMPANY.ordinal) {
            super.onCreateViewHolder(parent, viewType)
        } else {
            val loadingView = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.list_item_progress, parent, false)
            return LoadingViewHolder(loadingView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) != ITEM_TYPE.COMPANY.ordinal) {
            return
        }
        super.onBindViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == news.size - 1 && isShowLoadingIndicator) {
            ITEM_TYPE.LOADING.ordinal
        } else {
            ITEM_TYPE.COMPANY.ordinal
        }
    }

}