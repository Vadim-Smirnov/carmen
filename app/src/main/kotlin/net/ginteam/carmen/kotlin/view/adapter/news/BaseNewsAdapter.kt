package net.ginteam.carmen.kotlin.view.adapter.news

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.ginteam.carmen.kotlin.model.NewsModel

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

abstract class BaseNewsAdapter(protected val news: MutableList <NewsModel>,
                               val onNewsItemClick: (NewsModel) -> Unit,
                               val onFavoriteClick: (NewsModel, Boolean) -> Unit)
    : RecyclerView.Adapter <RecyclerView.ViewHolder>() {

    fun invalidateNewsItem(newsItem: NewsModel) {
        // we can try invalidate news item that out of memory
        // and search it by id
        val invalidatingNewsItem: NewsModel = news.find { newsItem.id == it.id }!!
        notifyItemChanged(news.indexOf(invalidatingNewsItem))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val newsItemViewHolder: ViewHolder? = holder as ViewHolder?

        // calculate item size and item photo size
        newsItemViewHolder?.bindData(news[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return LayoutInflater.from(parent?.context)
                .inflate(getItemLayoutResId(), parent, false).let {
            it.layoutParams = RecyclerView.LayoutParams(calculateItemWidth(), RecyclerView.LayoutParams.WRAP_CONTENT)
            ViewHolder(it, onNewsItemClick, onFavoriteClick)
        }
    }

    override fun getItemCount(): Int = news.size

    @LayoutRes
    protected abstract fun getItemLayoutResId(): Int

    protected abstract fun calculateItemWidth(): Int
    protected abstract fun calculatePhotoSize(): Int

    open class ViewHolder(itemView: View,
                          val onClick: (NewsModel) -> Unit,
                          val onFavoriteClick: (NewsModel, Boolean) -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bindData(news: NewsModel) {

        }
    }

}