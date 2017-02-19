package net.ginteam.carmen.kotlin.view.adapter.company

import net.ginteam.carmen.kotlin.model.CompanyModel

/**
 * Created by eugene_shcherbinock on 2/19/17.
 */
class FavoriteCompaniesAdapter(companies: MutableList <CompanyModel>,
                               onCompanyItemClick: (CompanyModel) -> Unit,
                               onFavoriteClick: (CompanyModel, Boolean) -> Unit)
    : VerticalCompaniesAdapter(companies, onCompanyItemClick, onFavoriteClick) {

    private var mLastRemovedPosition: Int? = null
    private var mLastRemovedCompany: CompanyModel? = null

    fun removeCompany(company: CompanyModel) {
        mLastRemovedCompany = company
        mLastRemovedPosition = companies.indexOf(mLastRemovedCompany!!)

        companies.removeAt(mLastRemovedPosition!!)
        notifyItemRemoved(mLastRemovedPosition!!)
    }

    fun restoreRemovedCompany() {
        companies.add(mLastRemovedPosition!!, mLastRemovedCompany!!)
        notifyItemInserted(mLastRemovedPosition!!)
    }

}