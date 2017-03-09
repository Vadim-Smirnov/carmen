package net.ginteam.carmen.kotlin.view.fragment.news

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.view.adapter.news.HorizontalNewsAdapter
import net.ginteam.carmen.view.adapter.RecyclerListHorizontalItemDecorator

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

abstract class BaseHorizontalNewsFragment<E : HorizontalNewsAdapter, in V : BaseNewsContract.View,
        T : BaseNewsContract.Presenter <V>> : BaseNewsFragment<E, V, T>() {

    abstract override fun showNews(news: MutableList<NewsModel>, pagination: PaginationModel?)

    override fun getRecyclerViewItemDecorator(): RecyclerView.ItemDecoration
            = RecyclerListHorizontalItemDecorator(context, R.dimen.vertical_list_item_spacing)

    override fun getRecyclerViewLayoutManager(): LinearLayoutManager
            = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}