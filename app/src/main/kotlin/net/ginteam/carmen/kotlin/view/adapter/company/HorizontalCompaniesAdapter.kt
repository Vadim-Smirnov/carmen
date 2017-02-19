package net.ginteam.carmen.kotlin.view.adapter.company

import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.utils.DisplayUtils

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

class HorizontalCompaniesAdapter(companies: MutableList <CompanyModel>,
                                 onCompanyItemClick: (CompanyModel) -> Unit,
                                 onFavoriteClick: (CompanyModel, Boolean) -> Unit)
    : BaseCompaniesAdapter(companies, onCompanyItemClick, onFavoriteClick) {

    private val VISIBLE_ITEMS_COUNT = 3

    override fun getItemLayoutResId(): Int = R.layout.list_item_company_horizontal

    override fun calculateItemWidth(): Int {
        val screenSize = DisplayUtils.getScreenSize(CarmenApplication.getContext())
        val itemSpacing = CarmenApplication.getContext().resources.getDimension(R.dimen.company_item_spacing).toInt()
        return screenSize.x / 2 - VISIBLE_ITEMS_COUNT * itemSpacing
    }

    override fun calculatePhotoSize(): Int {
        val screenSize = DisplayUtils.getScreenSize(CarmenApplication.getContext())
        val itemSpacing = CarmenApplication.getContext().resources.getDimension(R.dimen.company_item_spacing).toInt()
        return screenSize.x / 2 - VISIBLE_ITEMS_COUNT * itemSpacing
    }
}