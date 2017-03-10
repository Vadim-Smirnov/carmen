package net.ginteam.carmen;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Eugene on 12/21/16.
 */

public class CarmenApplication extends Application {

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

        Log.d("FirebaseService", "Token exists: " + FirebaseInstanceId.getInstance().getToken());

        setupFabricCrashlytics();
        setupCalligraphy();
    }

    private void setupFabricCrashlytics() {
        Log.d("CarmenApplication", "Is debug? -> " + BuildConfig.DEBUG);
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }

    private void setupCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.open_sans_light_font))
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

}
