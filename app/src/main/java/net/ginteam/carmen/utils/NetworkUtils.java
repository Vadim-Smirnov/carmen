package net.ginteam.carmen.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by vadik on 07.02.17.
 */

public class NetworkUtils {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
