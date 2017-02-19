package net.ginteam.carmen.kotlin.presenter.company

import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.BaseCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.AuthProvider
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider
import net.ginteam.carmen.kotlin.provider.CompaniesDataSourceProvider
import net.ginteam.carmen.kotlin.provider.OnlineCompaniesDataSourceProvider

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

open abstract class BaseCompaniesPresenter<V : BaseCompaniesContract.View> : BasePresenter <V>(),
        BaseCompaniesContract.Presenter <V> {

    protected var mAuthProvider: AuthProvider = AuthenticationProvider
    protected var mCompaniesProvider: CompaniesDataSourceProvider = OnlineCompaniesDataSourceProvider()

    override fun addCompanyToFavorites(company: CompanyModel) {
        if (!checkUserStatus()) {
            return
        }

        mCompaniesProvider
                .addUserFavoriteCompany(company.id)
                .subscribe(object : ModelSubscriber<String>() {
                    override fun success(model: String) {
                        company.isFavorite = true
                        mView?.showFavoriteConfirmationMessage(company, R.string.company_added_to_favorites_message)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showFavoriteErrorMessage(R.string.company_not_added_to_favorites_message)
                    }
                })
    }

    override fun removeCompanyFromFavorites(company: CompanyModel) {
        if (!checkUserStatus()) {
            return
        }

        mCompaniesProvider
                .removeUserFavoriteCompany(company.id)
                .subscribe(object : ModelSubscriber<String>() {
                    override fun success(model: String) {
                        company.isFavorite = false
                        mView?.showFavoriteConfirmationMessage(company, R.string.company_removed_from_favorites_message)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showFavoriteErrorMessage(R.string.company_not_removed_from_favorites_message)
                    }
                })
    }

    private fun checkUserStatus(): Boolean {
        if (mAuthProvider.currentCachedUser == null) {
            mView?.showMessage(R.string.access_denied_message)
            return false
        }
        return true
    }

}