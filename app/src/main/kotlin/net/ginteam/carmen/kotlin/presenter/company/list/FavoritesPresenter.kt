package net.ginteam.carmen.kotlin.presenter.company.list

import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.FavoriteCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */
class FavoritesPresenter : BaseCompaniesPresenter<FavoriteCompaniesContract.View>(), FavoriteCompaniesContract.Presenter {

    override fun fetchUserFavorites() {
        mView?.showLoading(true)

        mCompaniesProvider
                .fetchUserFavoriteCompanies()
                .subscribe(object : ModelSubscriber<MutableList <CompanyModel>>() {
                    override fun success(model: MutableList<CompanyModel>) {
                        mView?.showLoading(false)
                        mView?.showCompanies(model)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }

}