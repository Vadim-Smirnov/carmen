package net.ginteam.carmen.kotlin.view.fragment.news

import net.ginteam.carmen.kotlin.contract.PopularNewsContract
import net.ginteam.carmen.kotlin.presenter.news.PopularNewsPresenter
import net.ginteam.carmen.kotlin.view.adapter.news.VerticalNewsAdapter

/**
 * Created by vadik on 09.03.17.
 */
class PopularNewsFragment : BaseVerticalNewsFragment<VerticalNewsAdapter,
        PopularNewsContract.View, PopularNewsContract.Presenter>(), PopularNewsContract.View {

    override var mPresenter: PopularNewsContract.Presenter = PopularNewsPresenter()

    companion object {
        const val POSITION: Int = 1

        fun newInstance(): PopularNewsFragment = PopularNewsFragment()

    }

    override fun fetchNews() {
        mPresenter.fetchPopularNews(mCurrentPaginationPage)
    }
}