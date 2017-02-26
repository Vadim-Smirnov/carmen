package net.ginteam.carmen.kotlin.provider

import net.ginteam.carmen.kotlin.api.service.RatingService
import net.ginteam.carmen.kotlin.asyncWithCache
import net.ginteam.carmen.kotlin.model.InitialRating
import net.ginteam.carmen.kotlin.model.RatingModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/26/17.
 */

interface RatingDataSourceProvider {

    fun createRating(rating: InitialRating): Observable<ResponseModel<RatingModel>>
    fun updateRating(ratingId: Int, rating: RatingModel): Observable<ResponseModel<RatingModel>>

}

class OnlineRatingDataSourceProvider : RatingDataSourceProvider {
    private val ratingService: RatingService = RatingService.create()

    override fun createRating(rating: InitialRating): Observable<ResponseModel<RatingModel>>
            = ratingService.createRating(rating).asyncWithCache()

    override fun updateRating(ratingId: Int, rating: RatingModel): Observable<ResponseModel<RatingModel>>
            = ratingService.updateRating(ratingId, rating).asyncWithCache()
}