package net.ginteam.carmen.kotlin.presenter.news

import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.NewsDetailsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider
import net.ginteam.carmen.kotlin.provider.NewsDataSourceProvider
import net.ginteam.carmen.kotlin.provider.OnlineNewsDataSourceProvider

/**
 * Created by vadik on 10.03.17.
 */

class NewsDetailsPresenter : BasePresenter<NewsDetailsContract.View>(), NewsDetailsContract.Presenter {

    private val mNewsDataSourceProvider: NewsDataSourceProvider = OnlineNewsDataSourceProvider()

    override fun fetchNewsDetail(newsDetail: NewsModel) {
        mNewsDataSourceProvider
                .fetchNewsDetails(newsDetail.id)
                .subscribe(object : ModelSubscriber<NewsModel>() {
                    override fun success(model: NewsModel) {
                        mView?.showNewsDetails(model)
                        mView?.showPopularNews()
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }

    override fun isUserSignedIn(): Boolean {
        return AuthenticationProvider.currentCachedUser != null
    }

    override fun addNewsToFavorites(news: NewsModel) {
        mNewsDataSourceProvider
                .addUserFavoritesNews(news.id)
                .subscribe(object : ModelSubscriber<String>() {
                    override fun success(model: String) {
                        news.isFavorite = true
                        mView?.invalidateFavoriteIndicator(true)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                    }
                })
    }

    override fun removeNewsFromFavorites(news: NewsModel) {
        mNewsDataSourceProvider
                .removeUserFavoritesNews(news.id)
                .subscribe(object : ModelSubscriber<String>() {
                    override fun success(model: String) {
                        news.isFavorite = false
                        mView?.invalidateFavoriteIndicator(false)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                    }
                })
    }
}