package net.ginteam.carmen.kotlin.presenter.company

import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.RecentlyWatchedCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */
class RecentlyWatchedCompaniesPresenter : BaseCompaniesPresenter <RecentlyWatchedCompaniesContract.View>(), RecentlyWatchedCompaniesContract.Presenter {

    override fun fetchUserRecentlyWatched() {
        mView?.showLoading(true)

        mCompaniesProvider
                .fetchUserRecentlyWatched()
                .subscribe(object : ModelSubscriber <List <CompanyModel>>() {
                    override fun success(model: List<CompanyModel>) {
                        mView?.showLoading(false)
                        mView?.showCompanies(model.toMutableList())
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }
}