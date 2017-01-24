package net.ginteam.carmen.contract;

import android.support.annotation.IdRes;

import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

/**
 * Created by Eugene on 1/23/17.
 */

public interface MainContract {

    interface View extends BaseContract.View {

        void showFragment(@IdRes int containerId, BaseFetchingFragment fragment);

    }

    interface Presenter extends BaseContract.Presenter <View> {

        void onStart();

    }

}
