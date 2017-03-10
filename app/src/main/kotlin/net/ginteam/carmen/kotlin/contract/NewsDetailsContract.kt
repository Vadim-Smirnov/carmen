package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.NewsModel

/**
 * Created by vadik on 10.03.17.
 */

object NewsDetailsContract {

    interface View : BaseContract.View {

        fun showNewsDetails(newsDetail: NewsModel)
        fun invalidateFavoriteIndicator(isFavorite: Boolean)

        fun showPopularNews()

    }

    interface Presenter : BaseContract.Presenter <View> {

        fun fetchNewsDetail(newsDetail: NewsModel)
        fun isUserSignedIn(): Boolean
        fun addNewsToFavorites(news: NewsModel)
        fun removeNewsFromFavorites(news: NewsModel)

    }

}