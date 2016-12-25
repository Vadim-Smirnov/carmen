package net.ginteam.carmen;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Eugene on 12/21/16.
 */

public class CarmenApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.open_sans_light_font))
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

}
