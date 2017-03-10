package net.ginteam.carmen.utils;

import android.os.Build;
import android.provider.Settings;

import net.ginteam.carmen.CarmenApplication;

/**
 * Created by eugene_shcherbinock on 2/6/17.
 */

public class DeviceUtils {

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static String getDeviceId() {
        return Settings
                .Secure
                .getString(
                        CarmenApplication.getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID
                );
    }

    public static String getDeviceType() {
        return "android";
    }

}
