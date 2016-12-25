package net.ginteam.carmen.view.custom;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.ginteam.carmen.R;

/**
 * Created by Eugene on 12/25/16.
 */

public class ToolbarDrawerToggle extends ActionBarDrawerToggle {

    public ToolbarDrawerToggle(Activity activity, final DrawerLayout drawerLayout, Toolbar toolbar, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);

        setDrawerIndicatorEnabled(false);
        setHomeAsUpIndicator(R.drawable.ic_navigation_menu);

        setToolbarNavigationClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}
