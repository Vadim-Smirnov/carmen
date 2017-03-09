package net.ginteam.carmen.kotlin.presenter.news

import net.ginteam.carmen.kotlin.contract.PopularNewsContract

/**
 * Created by vadik on 09.03.17.
 */
class PopularNewsPresenter : BaseNewsPresenter<PopularNewsContract.View>(), PopularNewsContract.Presenter {

    override fun fetchPopularNews(pageNumber: Int) {
    }

}