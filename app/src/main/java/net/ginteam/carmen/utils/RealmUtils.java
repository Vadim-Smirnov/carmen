package net.ginteam.carmen.utils;

import android.util.Log;

import net.ginteam.carmen.CarmenApplication;
import net.ginteam.carmen.kotlin.Constants;
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by eugene_shcherbinock on 3/19/17.
 */

public class RealmUtils {

    public static RealmConfiguration getInitialConfiguration() {
        return new RealmConfiguration.Builder()
                .initialData(getInitializeTransaction())
                .name(Constants.Realm.DATABASE_NAME)
                .build();
    }

    private static Realm.Transaction getInitializeTransaction() {
        return new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    final InputStream inputStream = CarmenApplication
                            .getContext()
                            .getAssets()
                            .open(Constants.Realm.INIT_FILE_PATH);
                    realm.createAllFromJson(CostTypeModel.class, inputStream);
                    Log.d("RealmUtils", "File exported successful!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
