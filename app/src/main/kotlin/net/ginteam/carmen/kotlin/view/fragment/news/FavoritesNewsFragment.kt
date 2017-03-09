package net.ginteam.carmen.kotlin.view.fragment.news

import net.ginteam.carmen.kotlin.contract.FavoritesNewsContract
import net.ginteam.carmen.kotlin.presenter.news.FavoritesNewsPresenter
import net.ginteam.carmen.kotlin.view.adapter.news.VerticalNewsAdapter

/**
 * Created by vadik on 09.03.17.
 */
class FavoritesNewsFragment : BaseVerticalNewsFragment<VerticalNewsAdapter,
        FavoritesNewsContract.View, FavoritesNewsContract.Presenter>(), FavoritesNewsContract.View {

    override var mPresenter: FavoritesNewsContract.Presenter = FavoritesNewsPresenter()

    companion object {
        const val POSITION: Int = 2

        fun newInstance(): FavoritesNewsFragment = FavoritesNewsFragment()

    }

    override fun fetchNews() {
        mPresenter.fetchFavoritesNews(mCurrentPaginationPage)
    }

}