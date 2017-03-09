package net.ginteam.carmen.kotlin.contract

import android.support.annotation.StringRes
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.model.PaginationModel

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

object BaseNewsContract {

    interface View : BaseContract.View {

        fun showNews(news: MutableList <NewsModel>, pagination: PaginationModel? = null)
        fun showFavoriteConfirmationMessage(newsItem: NewsModel, @StringRes messageResId: Int)
        fun showFavoriteErrorMessage(@StringRes messageResId: Int)

    }

    interface Presenter <in V : View> : BaseContract.Presenter <V> {

        fun addNewsItemToFavorites(newsItem: NewsModel)
        fun removeNewsItemFromFavorites(newsItem: NewsModel)

    }

}

object RecentNewsContract {

    interface View : BaseNewsContract.View

    interface Presenter : BaseNewsContract.Presenter <View> {

        fun fetchRecentNews(pageNumber: Int = 1)

    }

}

object PopularNewsContract {

    interface View : BaseNewsContract.View

    interface Presenter : BaseNewsContract.Presenter <View> {

        fun fetchPopularNews(pageNumber: Int = 1)

    }

}

object FavoritesNewsContract {

    interface View : BaseNewsContract.View

    interface Presenter : BaseNewsContract.Presenter <View> {

        fun fetchFavoritesNews(pageNumber: Int = 1)

    }

}