package net.ginteam.carmen.kotlin.presenter.company

import net.ginteam.carmen.kotlin.api.response.MetaSubscriber
import net.ginteam.carmen.kotlin.contract.CompaniesContract
import net.ginteam.carmen.kotlin.manager.PreferencesManager
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */
class CompaniesPresenter : BaseCompaniesPresenter <CompaniesContract.View>(), CompaniesContract.Presenter {

    private var isFirsLoading: Boolean = true

    private val mPreferences: PreferencesManager = SharedPreferencesManager

    override fun fetchCompanies(categoryId: Int, filterQuery: String, sortField: String, sortType: String, pageNumber: Int) {
        isFirsLoading = pageNumber == 1
        if (isFirsLoading) {
            mView?.showLoading(true)
        }

        val filter = filterQuery.plus("cityId:${mPreferences.userCityModel!!.id}")
        mCompaniesProvider
                .fetchCompanies(categoryId, filter, sortField, sortType, pageNumber)
                .subscribe(object : MetaSubscriber <MutableList <CompanyModel>>() {
                    override fun success(model: MutableList<CompanyModel>, pagination: PaginationModel) {
                        mView?.showLoading(false)
                        if (isFirsLoading) {
                            mView?.showCompanies(model, pagination)
                            return
                        }
                        mView?.showCompanies(model)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }

}