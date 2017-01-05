package net.ginteam.carmen.contract;

/**
 * Created by Eugene on 12/23/16.
 */

public interface FetchContract {

    interface View extends BaseContract.View {

        void showLoading(boolean isLoading);

        void showError(String message);

    }

    interface Presenter <V extends View> extends BaseContract.Presenter <V> {



    }

}
