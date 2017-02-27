package net.ginteam.carmen.kotlin.presenter.company.detail

import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.CompanyRatingUpdateContract
import net.ginteam.carmen.kotlin.model.RatingModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.OnlineRatingDataSourceProvider
import net.ginteam.carmen.kotlin.provider.RatingDataSourceProvider

/**
 * Created by eugene_shcherbinock on 2/27/17.
 */

class CompanyRatingUpdatePresenter : BasePresenter <CompanyRatingUpdateContract.View>(), CompanyRatingUpdateContract.Presenter {

    private val mRatingsDataSourceProvider: RatingDataSourceProvider = OnlineRatingDataSourceProvider()

    override fun sendCompanyRating(rating: RatingModel) {
        mView?.showLoading(true)

        mRatingsDataSourceProvider
                .updateRating(rating.id, rating)
                .subscribe(object : ModelSubscriber<RatingModel>() {
                    override fun success(model: RatingModel) {
                        mView?.showLoading(false)
                        mView?.dismiss()
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }
}