package net.ginteam.carmen.kotlin.view.fragment.news

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.view.adapter.news.VerticalNewsAdapter
import net.ginteam.carmen.view.adapter.RecyclerListVerticalItemDecorator

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

abstract class BaseVerticalNewsFragment<E : VerticalNewsAdapter, in V : BaseNewsContract.View,
        T : BaseNewsContract.Presenter <V>> : BasePaginatableNewsFragment<E, V, T>() {

    override fun getLayoutResId(): Int = R.layout.fragment_news_list

    override fun initializeAdapter(news: MutableList<NewsModel>,
                                   onItemClick: (NewsModel) -> Unit,
                                   onFavoriteClick: (NewsModel, Boolean) -> Unit
    ): E = VerticalNewsAdapter(news, onItemClick, onFavoriteClick) as E

    override fun getRecyclerViewItemDecorator(): RecyclerView.ItemDecoration
            = RecyclerListVerticalItemDecorator(context, R.dimen.vertical_list_item_spacing)

    override fun getRecyclerViewLayoutManager(): LinearLayoutManager
            = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}