package net.ginteam.carmen.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

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

}
