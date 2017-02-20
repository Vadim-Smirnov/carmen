package net.ginteam.carmen.network.api.service;

import net.ginteam.carmen.model.Rating;
import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.filter.CreateRating;
import net.ginteam.carmen.network.api.ApiLinks;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vadik on 20.02.17.
 */

public interface RatingService {

    @POST(ApiLinks.CATALOG.SET_RATING)
    Observable<ResponseModel<Rating>> setRating(
            @Body CreateRating createRating
    );

    @PUT(ApiLinks.CATALOG.UPDATE_RATING)
    Observable<ResponseModel<Rating>> updateRating(
            @Path(ApiLinks.CATALOG.ID) int ratingId,
            @Body Rating rating
    );

}
