package net.ginteam.carmen.utils;

import android.os.Build;

/**
 * Created by eugene_shcherbinock on 2/6/17.
 */

public class DeviceUtils {

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
