package net.ginteam.carmen.presenter.company;

import net.ginteam.carmen.contract.company.VoteObjectContract;
import net.ginteam.carmen.model.Rating;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.company.RatingProvider;

/**
 * Created by vadik on 20.02.17.
 */

public class VoteObjectPresenter implements VoteObjectContract.Presenter {

    private VoteObjectContract.View mView;

    @Override
    public void attachView(VoteObjectContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void sendReview(Rating rating) {
        RatingProvider.getInstance().updateRating(rating, new ModelCallback<Rating>() {
            @Override
            public void onSuccess(Rating resultModel) {
                mView.onSuccess();
            }

            @Override
            public void onFailure(String message) {
                mView.showError(message);
            }
        });
    }
}
