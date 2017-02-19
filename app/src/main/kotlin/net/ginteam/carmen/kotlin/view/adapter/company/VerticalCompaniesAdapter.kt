package net.ginteam.carmen.kotlin.view.adapter.company

import android.support.v7.widget.RecyclerView
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.utils.DisplayUtils

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

class VerticalCompaniesAdapter(companies: MutableList <CompanyModel>,
                               onCompanyItemClick: (CompanyModel) -> Unit,
                               onFavoriteClick: (CompanyModel, Boolean) -> Unit)
    : BaseCompaniesAdapter(companies, onCompanyItemClick, onFavoriteClick) {

    override fun getItemLayoutResId(): Int = R.layout.list_item_company_vertical

    override fun calculateItemWidth(): Int = RecyclerView.LayoutParams.MATCH_PARENT

    override fun calculatePhotoSize(): Int {
        val screenSize = DisplayUtils.getScreenSize(CarmenApplication.getContext())
        return 43 * screenSize.x / 100
    }

}