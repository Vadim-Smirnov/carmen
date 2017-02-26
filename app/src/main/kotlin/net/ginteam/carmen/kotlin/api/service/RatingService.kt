package net.ginteam.carmen.kotlin.api.service

import net.ginteam.carmen.kotlin.api.ApiSettings
import net.ginteam.carmen.kotlin.manager.ApiManager
import net.ginteam.carmen.kotlin.model.InitialRating
import net.ginteam.carmen.kotlin.model.RatingModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/26/17.
 */

interface RatingService {

    @POST(ApiSettings.Catalog.CREATE_RATING)
    fun createRating(@Body rating: InitialRating): Observable<ResponseModel<RatingModel>>

    @PUT(ApiSettings.Catalog.UPDATE_RATING)
    fun updateRating(
            @Path(ApiSettings.Catalog.Params.ID) ratingId: Int,
            @Body rating: RatingModel
    ): Observable<ResponseModel<RatingModel>>

    companion object {
        fun create(): RatingService = ApiManager.retrofit.create(RatingService::class.java)
    }

}