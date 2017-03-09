package net.ginteam.carmen.kotlin.view.fragment.news

import android.util.Log
import net.ginteam.carmen.kotlin.contract.RecentNewsContract
import net.ginteam.carmen.kotlin.presenter.news.RecentNewsPresenter
import net.ginteam.carmen.kotlin.view.adapter.news.VerticalNewsAdapter

/**
 * Created by vadik on 09.03.17.
 */
class RecentNewsFragment : BaseVerticalNewsFragment<VerticalNewsAdapter,
        RecentNewsContract.View, RecentNewsContract.Presenter>(), RecentNewsContract.View {

    override var mPresenter: RecentNewsContract.Presenter = RecentNewsPresenter()

    companion object {
        const val POSITION: Int = 0

        fun newInstance(): RecentNewsFragment = RecentNewsFragment()

    }

    override fun fetchNews() {
        mPresenter.fetchRecentNews(mCurrentPaginationPage)
        Log.e("NEWS", "CurrentPaginationPage:$mCurrentPaginationPage")
    }
}