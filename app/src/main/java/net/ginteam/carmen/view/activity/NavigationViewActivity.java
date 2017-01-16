package net.ginteam.carmen.view.activity;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.provider.auth.AuthProvider;
import net.ginteam.carmen.view.custom.ToolbarDrawerToggle;

/**
 * Created by Eugene on 12/27/16.
 */

public class NavigationViewActivity extends ToolbarActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;

    protected Fragment mCurrentFragment;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initializeNavigationView();
        if (AuthProvider.getInstance().getCurrentCachedUser() != null) {
            updateNavigationHeader();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void prepareFragment(Fragment fragment, boolean isNeedBackStack) {
        mCurrentFragment = fragment;

        if (isNeedBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }

    private void initializeNavigationView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ToolbarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        mDrawerLayout.addDrawerListener(toggle);
        mNavigationView.setNavigationItemSelectedListener(this);

        disableNavigationViewScrollbars(mNavigationView);

    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        if (navigationMenuView != null) {
            navigationMenuView.setVerticalScrollBarEnabled(false);
        }
    }

    private void updateNavigationHeader() {
        View navigationHeader = mNavigationView.getHeaderView(0);

        TextView textViewName = (TextView) navigationHeader.findViewById(R.id.text_view_nav_header_name);
        TextView textViewEmail = (TextView) navigationHeader.findViewById(R.id.text_view_nav_header_email);

        textViewName.setText(AuthProvider.getInstance().getCurrentCachedUser().getName());
        textViewEmail.setText(AuthProvider.getInstance().getCurrentCachedUser().getEmail());
    }

}
