package net.ginteam.carmen.kotlin.presenter.news

import net.ginteam.carmen.kotlin.contract.FavoritesNewsContract

/**
 * Created by vadik on 09.03.17.
 */
class FavoritesNewsPresenter : BaseNewsPresenter<FavoritesNewsContract.View>(), FavoritesNewsContract.Presenter {

    override fun fetchFavoritesNews(pageNumber: Int) {
    }

}