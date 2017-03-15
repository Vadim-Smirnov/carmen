package net.ginteam.carmen.kotlin.view.fragment.news

import android.os.Bundle
import net.ginteam.carmen.kotlin.contract.PopularNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.presenter.news.PopularNewsPresenter
import net.ginteam.carmen.kotlin.view.adapter.news.VerticalNewsAdapter

/**
 * Created by vadik on 09.03.17.
 */
class PopularNewsFragment : BaseVerticalNewsFragment<VerticalNewsAdapter,
        PopularNewsContract.View, PopularNewsContract.Presenter>(), PopularNewsContract.View {

    override var mPresenter: PopularNewsContract.Presenter = PopularNewsPresenter()

    private var mDays: Int = 30

    companion object {
        private const val DAYS_ARGUMENT = "days_argument"
        const val POSITION: Int = 1

        fun newInstance(forDays: Int = 30): PopularNewsFragment {
            val bundle = Bundle()
            bundle.putInt(DAYS_ARGUMENT, forDays)

            val instance = PopularNewsFragment()
            instance.arguments = bundle
            return instance
        }
    }

    override fun fetchNews() {
        mPresenter.fetchPopularNews(forDays = mDays, pageNumber = mCurrentPaginationPage)
    }

    override fun updateDependencies() {
        super.updateDependencies()

        mDays = arguments.getInt(DAYS_ARGUMENT)
    }
}