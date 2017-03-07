package net.ginteam.carmen.kotlin.view.adapter.news

import android.support.v7.widget.RecyclerView
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.utils.DisplayUtils

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

open class VerticalNewsAdapter(news: MutableList <NewsModel>,
                               onNewsItemClick: (NewsModel) -> Unit,
                               onFavoriteClick: (NewsModel, Boolean) -> Unit)
    : PaginatableNewsAdapter(news, onNewsItemClick, onFavoriteClick) {

    override fun getItemLayoutResId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun calculateItemWidth(): Int = RecyclerView.LayoutParams.MATCH_PARENT

    override fun calculatePhotoSize(): Int {
        val screenSize = DisplayUtils.getScreenSize(CarmenApplication.getContext())
        return 43 * screenSize.x / 100
    }

}