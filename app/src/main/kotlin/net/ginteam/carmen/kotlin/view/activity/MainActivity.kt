package net.ginteam.carmen.kotlin.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import android.widget.TextView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.MainActivityContract
import net.ginteam.carmen.kotlin.disableScrollbars
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.UserModel
import net.ginteam.carmen.kotlin.prepareFragment
import net.ginteam.carmen.kotlin.presenter.MainActivityPresenter
import net.ginteam.carmen.kotlin.view.activity.authentication.SignInActivity
import net.ginteam.carmen.kotlin.view.fragment.category.CategoriesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.*
import net.ginteam.carmen.view.custom.ToolbarDrawerToggle

class MainActivity : BaseActivity <MainActivityContract.View, MainActivityContract.Presenter>(),
        MainActivityContract.View, NavigationView.OnNavigationItemSelectedListener,
        CategoriesFragment.OnCategorySelectedListener, BaseCompaniesFragment.OnCompanySelectedListener,
        CompaniesFragment.OnBottomMenuItemSelectedListener {

    override var mPresenter: MainActivityContract.Presenter = MainActivityPresenter()

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.checkUserStatus()

        prepareFragment(R.id.main_fragment_container, RecentlyWatchedCompaniesFragment.newInstance(false))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_item -> mPresenter.localUserLogout()
        }
        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
            return
        }
        super.onBackPressed()
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    /* MainActivityContract.View implementation */

    override fun inflateNavigationView(menuResId: Int, headerLayoutResId: Int) {
        mNavigationView.menu.clear()
        mNavigationView.removeHeaderView(mNavigationView.getHeaderView(0))

        mNavigationView.inflateMenu(menuResId)
        mNavigationView.inflateHeaderView(headerLayoutResId)

        // if user does not signed in
        if (headerLayoutResId == R.layout.navigation_view_default_header) {
            mNavigationView.getHeaderView(0).findViewById(R.id.button_sign_in).setOnClickListener {
                showSignInActivity()
            }
        }
    }

    override fun showUserInformation(user: UserModel) {
        val headerView = mNavigationView.getHeaderView(0)
        (headerView.findViewById(R.id.text_view_nav_header_name) as TextView).text = user.name
        (headerView.findViewById(R.id.text_view_nav_header_email) as TextView).text = user.email
    }

    override fun showSignInActivity() {
        val intent = Intent(getContext(), SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    /* -------------------------------------- */

    /* Fragments listeners */

    override fun onCategorySelected(category: CategoryModel, fromDialogSelection: Boolean) {
        prepareFragment(R.id.main_fragment_container, CompaniesFragment.newInstance(category))
    }

    override fun onCompanySelected(company: CompanyModel) {

    }

    override fun onShowMap(category: CategoryModel) {

    }

    override fun onShowCategoriesDialog() {

    }

    override fun onShowFiltersActivity(category: CategoryModel) {

    }

    override fun onShowSortDialog() {

    }

    /* -------------------------------------- */

    /* Private */

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        mNavigationView = findViewById(R.id.navigation_view) as NavigationView

        updateNavigationViewDependencies()
    }

    private fun updateNavigationViewDependencies() {
        val toggle = ToolbarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState()
        mDrawerLayout.addDrawerListener(toggle)

        mNavigationView.disableScrollbars()
        mNavigationView.setNavigationItemSelectedListener(this)
    }

    /* -------------------------------------- */

}