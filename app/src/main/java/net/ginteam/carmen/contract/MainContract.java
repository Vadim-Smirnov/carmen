package net.ginteam.carmen.contract;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vadik on 27.12.16.
 */

public interface MainContract {

    interface View extends BaseContract.View {

        AppCompatActivity getActivity();

    }

    interface Presenter extends BaseContract.Presenter<View> {

        void getLastLocation();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    }

}
