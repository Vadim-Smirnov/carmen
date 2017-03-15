package net.ginteam.carmen.kotlin.presenter.company.list

import net.ginteam.carmen.kotlin.api.response.MetaSubscriber
import net.ginteam.carmen.kotlin.contract.FavoriteCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */
class FavoritesPresenter : BaseCompaniesPresenter<FavoriteCompaniesContract.View>(), FavoriteCompaniesContract.Presenter {

    private var isFirsLoading: Boolean = true

    override fun fetchUserFavorites(pageNumber: Int) {
        isFirsLoading = pageNumber == 1
        if (isFirsLoading) {
            mView?.showLoading(true)
        }

        mCompaniesProvider
                .fetchUserFavoriteCompanies()
                .subscribe(object : MetaSubscriber<MutableList <CompanyModel>>() {
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