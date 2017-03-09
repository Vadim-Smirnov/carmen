package net.ginteam.carmen.kotlin.presenter.news

import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.AuthProvider
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider
import net.ginteam.carmen.kotlin.provider.NewsDataSourceProvider
import net.ginteam.carmen.kotlin.provider.OnlineNewsDataSourceProvider

/**
 * Created by eugene_shcherbinock on 3/9/17.
 */

abstract class BaseNewsPresenter <V : BaseNewsContract.View> : BasePresenter <V>(),
        BaseNewsContract.Presenter <V> {

    protected var mAuthProvider: AuthProvider = AuthenticationProvider
    protected val mNewsDataSourceProvider: NewsDataSourceProvider = OnlineNewsDataSourceProvider()

    override fun addNewsItemToFavorites(newsItem: NewsModel) {
        if (!checkUserStatus()) {
            return
        }
    }

    override fun removeNewsItemFromFavorites(newsItem: NewsModel) {
        if (!checkUserStatus()) {
            return
        }
    }

    private fun checkUserStatus(): Boolean {
        if (mAuthProvider.currentCachedUser == null) {
            mView?.showMessage(R.string.access_denied_message)
            return false
        }
        return true
    }
}