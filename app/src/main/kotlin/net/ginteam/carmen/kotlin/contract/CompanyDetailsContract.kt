package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.ComfortModel
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.RatingModel

/**
 * Created by eugene_shcherbinock on 2/14/17.
 */

object CompanyDetailsContract {

    interface View : BaseContract.View {

        fun showCompanyDetails(company: CompanyModel)
        fun invalidateFavoriteIndicator(isFavorite: Boolean)
        fun showRatingActivity(rating: RatingModel)

        fun showPopularCompanies()
        fun showComforts(comforts: List <ComfortModel>)
        fun showRatings(ratings: List <RatingModel>)
        fun showCategoryServices(categories: List <CategoryModel>)

    }

    interface Presenter : BaseContract.Presenter <View> {

        fun fetchCompanyDetail(company: CompanyModel)
        fun createRating(company: CompanyModel, ratingValue: Float)
        fun isUserSignedIn(): Boolean
        fun addCompanyToFavorites(company: CompanyModel)
        fun removeCompanyFromFavorites(company: CompanyModel)

    }

}