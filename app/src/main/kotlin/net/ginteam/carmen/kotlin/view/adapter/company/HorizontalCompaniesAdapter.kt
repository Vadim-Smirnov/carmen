package net.ginteam.carmen.kotlin.view.adapter.company

import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.utils.DisplayUtils

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

open class HorizontalCompaniesAdapter(companies: MutableList <CompanyModel>,
                                 onCompanyItemClick: (CompanyModel) -> Unit,
                                 onFavoriteClick: (CompanyModel, Boolean) -> Unit)
    : BaseCompaniesAdapter(companies, onCompanyItemClick, onFavoriteClick) {

    open protected var FULL_VISIBLE_ITEMS_COUNT: Int = 2
    open protected var VISIBLE_ITEMS_COUNT: Int = 3

    override fun getItemLayoutResId(): Int = R.layout.list_item_company_horizontal

    override fun calculateItemWidth(): Int {
        val screenSize = DisplayUtils.getScreenSize(CarmenApplication.getContext())
        val itemSpacing = CarmenApplication.getContext().resources.getDimension(R.dimen.company_item_spacing).toInt()
        return (screenSize.x / FULL_VISIBLE_ITEMS_COUNT) - (VISIBLE_ITEMS_COUNT * itemSpacing)
    }

    override fun calculatePhotoSize(): Int {
        return calculateItemWidth()
    }
}