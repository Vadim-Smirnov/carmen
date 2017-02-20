package net.ginteam.carmen.provider.company;

import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.model.Rating;
import net.ginteam.carmen.model.filter.CreateRating;
import net.ginteam.carmen.network.api.service.RatingService;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriber;
import net.ginteam.carmen.provider.ModelCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vadik on 20.02.17.
 */

public class RatingProvider {

    private static RatingProvider sInstance;

    private Rating mRating;

    private RatingService mRatingService;

    private RatingProvider() {
        mRatingService = ApiManager.getInstance().getService(RatingService.class);
    }

    public static RatingProvider getInstance() {
        if (sInstance == null) {
            sInstance = new RatingProvider();
        }
        return sInstance;
    }

    public void sendRating(CreateRating createRating, final ModelCallback<Rating> completion) {
        mRatingService
                .setRating(createRating)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<Rating>() {
                    @Override
                    public void onSuccess(Rating resultModel) {
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

    public void updateRating(Rating rating, final ModelCallback<Rating> completion) {
        mRatingService
                .updateRating(rating.getId(), rating)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<Rating>() {
                    @Override
                    public void onSuccess(Rating resultModel) {
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

}

