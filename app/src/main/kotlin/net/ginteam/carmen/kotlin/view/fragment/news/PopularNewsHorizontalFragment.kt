package net.ginteam.carmen.kotlin.view.fragment.news

import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.PopularNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.presenter.news.PopularNewsPresenter
import net.ginteam.carmen.kotlin.view.adapter.news.HorizontalNewsAdapter

/**
 * Created by vadik on 10.03.17.
 */

class PopularNewsHorizontalFragment: BaseHorizontalNewsFragment<HorizontalNewsAdapter,
        PopularNewsContract.View, PopularNewsContract.Presenter>(), PopularNewsContract.View {

    override var mPresenter: PopularNewsContract.Presenter = PopularNewsPresenter()

    override lateinit var mNewsAdapter: HorizontalNewsAdapter

    companion object {

        fun newInstance(): PopularNewsHorizontalFragment = PopularNewsHorizontalFragment()

    }

    override fun showNews(news: MutableList<NewsModel>, pagination: PaginationModel?) {
        mNewsAdapter = HorizontalNewsAdapter(news, this, this)
        mRecyclerViewNews.adapter = mNewsAdapter
    }

    override fun getLayoutResId(): Int = R.layout.fragment_news_list_horizontal

    override fun fetchNews() {
        mPresenter.fetchPopularNews()
    }
}