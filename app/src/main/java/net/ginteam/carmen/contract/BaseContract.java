package net.ginteam.carmen.contract;

import android.content.Context;

/**
 * Created by Eugene on 12/23/16.
 */

public interface BaseContract {

    interface View {

        Context getContext();

    }

    interface Presenter <V extends View> {

        void attachView(V view);

        void detachView();

    }

}
