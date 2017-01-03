package net.ginteam.carmen.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.ginteam.carmen.CarmenApplication;

/**
 * Created by vadik on 27.09.16.
 */

public class ActivityUtils {

    public static void showActivity(
            Class <? extends AppCompatActivity> activityClass, @Nullable Bundle arguments, boolean newTask) {
        Intent intent = new Intent(CarmenApplication.getContext(), activityClass);
        if (arguments != null) {
            intent.putExtras(arguments);
        }
        if (newTask) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        CarmenApplication.getContext().startActivity(intent);
    }

}
