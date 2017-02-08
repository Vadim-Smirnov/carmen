package net.ginteam.carmen.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import net.ginteam.carmen.CarmenApplication;

/**
 * Created by eugene_shcherbinock on 2/1/17.
 */

public class DisplayUtils {

    private static WindowManager sWindowManager;

    public static Point getScreenSize(Context context) {
        if (sWindowManager == null) {
            sWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }

        Point screenSizes = new Point();
        sWindowManager.getDefaultDisplay().getSize(screenSizes);
        return screenSizes;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

}
