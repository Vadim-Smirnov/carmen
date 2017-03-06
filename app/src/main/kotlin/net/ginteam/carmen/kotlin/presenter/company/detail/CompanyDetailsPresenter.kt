package net.ginteam.carmen.kotlin.presenter.company.detail

import net.ginteam.carmen.kotlin.api.ApiSettings
import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.CompanyDetailsContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.InitialRating
import net.ginteam.carmen.kotlin.model.RatingModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.*
import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 2/26/17.
 */
class CompanyDetailsPresenter : BasePresenter<CompanyDetailsContract.View>(), CompanyDetailsContract.Presenter {

    private val mCompaniesDataSourceProvider: CompaniesDataSourceProvider = OnlineCompaniesDataSourceProvider()

    override fun fetchCompanyDetail(company: CompanyModel) {
        mView?.showLoading(true)

        var relations: String = ""
        with(ApiSettings.Catalog.Relations) {
            relations = String.format("%s,%s,%s,%s,%s",
                    COMFORTS, DETAILS, CATEGORIES, RATINGS, USER_RATING)
        }

        mCompaniesDataSourceProvider
                .fetchCompanyDetails(company.id, relations)
                .subscribe(object : ModelSubscriber<CompanyModel>() {
                    override fun success(model: CompanyModel) {
                        mView?.showCompanyDetails(model)

                        if (notNullAndNotEmpty(model.categories)) {
                            mView?.showCategoryServices(model.categories!!.data)
                        }
                        if (notNullAndNotEmpty(model.comforts)) {
                            mView?.showComforts(model.comforts!!.data)
                        }
                        if (notNullAndNotEmpty(model.ratings)) {
                            mView?.showRatings(model.ratings!!.data)
                        }

                        mView?.showPopularCompanies()
                        mView?.showLoading(false)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }

    override fun createRating(company: CompanyModel, ratingValue: Float) {
        mView?.showLoading(true)

        val ratingDataSourceProvider: RatingDataSourceProvider = OnlineRatingDataSourceProvider()
        val initialRating: InitialRating = InitialRating(company.id, ratingValue)
        ratingDataSourceProvider
                .createRating(initialRating)
                .subscribe(object : ModelSubscriber<RatingModel>() {
                    override fun success(model: RatingModel) {
                        mView?.showLoading(false)
                        mView?.showUpdateRatingActivity(model)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }

    override fun isUserSignedIn(): Boolean {
        return AuthenticationProvider.currentCachedUser != null
    }

    override fun addCompanyToFavorites(company: CompanyModel) {
        mCompaniesDataSourceProvider
                .addUserFavoriteCompany(company.id)
                .subscribe(object : ModelSubscriber<String>() {
                    override fun success(model: String) {
                        company.isFavorite = true
                        mView?.invalidateFavoriteIndicator(true)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                    }
                })
    }

    override fun removeCompanyFromFavorites(company: CompanyModel) {
        mCompaniesDataSourceProvider
                .removeUserFavoriteCompany(company.id)
                .subscribe(object : ModelSubscriber<String>() {
                    override fun success(model: String) {
                        company.isFavorite = false
                        mView?.invalidateFavoriteIndicator(false)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                    }
                })
    }

    private fun <E : Serializable> notNullAndNotEmpty(responseModel: ResponseModel<List <E>>?): Boolean {
        responseModel?.let {
            return it.data.isNotEmpty()
        }
        return false
    }
}