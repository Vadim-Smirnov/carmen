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
        fun removeNewsItemyFromFavorites(newsItem: NewsModel)

    }

}