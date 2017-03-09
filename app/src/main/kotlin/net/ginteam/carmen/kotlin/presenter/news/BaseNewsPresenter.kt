package net.ginteam.carmen.kotlin.presenter.news

import net.ginteam.carmen.kotlin.contract.BaseNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.AuthProvider
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider

/**
 * Created by eugene_shcherbinock on 3/9/17.
 */

abstract class BaseNewsPresenter <V : BaseNewsContract.View> : BasePresenter <V>(),
        BaseNewsContract.Presenter <V> {

    protected var mAuthProvider: AuthProvider = AuthenticationProvider

    override fun addNewsItemToFavorites(newsItem: NewsModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeNewsItemFromFavorites(newsItem: NewsModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}