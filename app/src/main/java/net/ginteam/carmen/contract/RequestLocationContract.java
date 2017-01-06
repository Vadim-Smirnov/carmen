package net.ginteam.carmen.contract;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vadik on 27.12.16.
 */

public interface RequestLocationContract {

    interface View extends BaseContract.View {

        AppCompatActivity getActivity();

        void showCityListView();

    }

    interface Presenter <V extends View> extends BaseContract.Presenter <V> {

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    }

}
