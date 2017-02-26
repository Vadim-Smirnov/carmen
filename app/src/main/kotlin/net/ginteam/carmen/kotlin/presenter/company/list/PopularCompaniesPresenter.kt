package net.ginteam.carmen.kotlin.presenter.company.list

import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.PopularCompaniesContract
import net.ginteam.carmen.kotlin.manager.PreferencesManager
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.CompanyModel

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */
class PopularCompaniesPresenter : BaseCompaniesPresenter<PopularCompaniesContract.View>(), PopularCompaniesContract.Presenter {

    private val mPreferences: PreferencesManager = SharedPreferencesManager

    override fun fetchPopularCompanies() {
        mView?.showLoading(true)

        mCompaniesProvider
                .fetchPopularCompanies(mPreferences.userCityModel!!.id)
                .subscribe(object : ModelSubscriber<List <CompanyModel>>() {
                    override fun success(model: List<CompanyModel>) {
                        mView?.showLoading(false)
                        mView?.showCompanies(model.toMutableList())
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }

    override fun getUserCityName(): String = mPreferences.userCityModel!!.name
}