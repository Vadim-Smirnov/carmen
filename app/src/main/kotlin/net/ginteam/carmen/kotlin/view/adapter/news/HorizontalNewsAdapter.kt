package net.ginteam.carmen.kotlin.view.adapter.news

import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.utils.DisplayUtils

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

open class HorizontalNewsAdapter(news: MutableList <NewsModel>,
                                 onNewsItemClick: (NewsModel) -> Unit,
                                 onFavoriteClick: (NewsModel, Boolean) -> Unit)
    : BaseNewsAdapter(news, onNewsItemClick, onFavoriteClick) {

    open protected var FULL_VISIBLE_ITEMS_COUNT: Int = 2
    open protected var VISIBLE_ITEMS_COUNT: Int = 3

    override fun getItemLayoutResId(): Int = R.layout.list_item_news_horizontal

    override fun calculateItemWidth(): Int {
        val screenSize = DisplayUtils.getScreenSize(CarmenApplication.getContext())
        val itemSpacing = CarmenApplication.getContext().resources.getDimension(R.dimen.vertical_list_item_spacing).toInt()
        return (screenSize.x / FULL_VISIBLE_ITEMS_COUNT) - (VISIBLE_ITEMS_COUNT * itemSpacing)
    }

    override fun calculatePhotoSize(): Int {
        return calculateItemWidth()
    }

}