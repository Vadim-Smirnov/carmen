package net.ginteam.carmen.view.activity;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.manager.PreferencesManager;
import net.ginteam.carmen.model.auth.UserModel;
import net.ginteam.carmen.provider.auth.AuthProvider;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.view.activity.auth.SignInActivity;
import net.ginteam.carmen.view.custom.ToolbarDrawerToggle;
import net.ginteam.carmen.view.fragment.MainFragment;
import net.ginteam.carmen.view.fragment.category.CategoryListFragment;
import net.ginteam.carmen.view.fragment.company.CompanyListFragment;

/**
 * Created by Eugene on 12/27/16.
 */

public class NavigationViewActivity extends FragmentsActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initializeNavigationView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (AuthProvider.getInstance().getCurrentCachedUser() != null) {
            mNavigationView.getMenu().clear();
            mNavigationView.removeHeaderView(mNavigationView.getHeaderView(0));
            mNavigationView.inflateMenu(R.menu.navigation_menu_full);
            mNavigationView.inflateHeaderView(R.layout.navigation_view_user_header);
            setupUserCredentialsInHeader(mNavigationView.getHeaderView(0), AuthProvider.getInstance().getCurrentCachedUser());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.main_item:
                selectedFragment = MainFragment.newInstance();
                break;

            case R.id.category_item:
                selectedFragment = CategoryListFragment.newInstance(false);
                break;

            case R.id.favorite_item:
                selectedFragment = CompanyListFragment
                        .newInstance(CompanyListFragment.COMPANY_LIST_TYPE.VERTICAL,
                                CompanyListFragment.FETCH_COMPANY_TYPE.FAVORITE, null);
                break;

            case R.id.recent_item:
                selectedFragment = CompanyListFragment
                        .newInstance(CompanyListFragment.COMPANY_LIST_TYPE.VERTICAL,
                                CompanyListFragment.FETCH_COMPANY_TYPE.RECENTLY_WATCHED, null);
                break;

            case R.id.profile_item:
                return true;

            case R.id.sign_in_item:
                ActivityUtils.showActivity(SignInActivity.class, null, true);
                return true;

            case R.id.logout_item:
                PreferencesManager.getInstance().setUserToken(null);
                AuthProvider.getInstance().setCurrentCachedUser(null);
                ActivityUtils.showActivity(SignInActivity.class, null, true);
                return true;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        prepareFragment(selectedFragment, false);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void showMainFragment() {
        onNavigationItemSelected(mNavigationView.getMenu()
                .getItem(AuthProvider.getInstance().getCurrentCachedUser() != null ? 0 : 1));
    }

    private void initializeNavigationView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ToolbarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

    private void setupUserCredentialsInHeader(View headerView, UserModel user) {
        TextView textViewName = (TextView) headerView.findViewById(R.id.text_view_nav_header_name);
        TextView textViewEmail = (TextView) headerView.findViewById(R.id.text_view_nav_header_email);

        textViewName.setText(user.getName());
        textViewEmail.setText(user.getEmail());
    }

}
