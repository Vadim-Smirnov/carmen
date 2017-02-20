package net.ginteam.carmen.contract.company;

import net.ginteam.carmen.contract.BaseContract;
import net.ginteam.carmen.model.Rating;

/**
 * Created by vadik on 20.02.17.
 */

public interface VoteObjectContract {

    interface View extends BaseContract.View {

        void showError(String message);

        void onSuccess();

    }

    interface Presenter extends BaseContract.Presenter <View> {

        void sendReview(Rating rating);

    }

}
