package net.ginteam.carmen.kotlin.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
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
import net.ginteam.carmen.kotlin.view.fragment.MainFragment
import net.ginteam.carmen.kotlin.view.fragment.category.CategoriesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.BaseCompaniesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.CompaniesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.FavoritesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.RecentlyWatchedCompaniesFragment
import net.ginteam.carmen.view.custom.ToolbarDrawerToggle

class MainActivity : BaseActivity <MainActivityContract.View, MainActivityContract.Presenter>(),
        MainActivityContract.View, NavigationView.OnNavigationItemSelectedListener,
        CategoriesFragment.OnCategorySelectedListener, BaseCompaniesFragment.OnCompanySelectedListener,
        CompaniesFragment.OnBottomMenuItemSelectedListener {

    override var mPresenter: MainActivityContract.Presenter = MainActivityPresenter()

    private var mLastAccessibleItem: MenuItem? = null
    private var mCurrentFragment: Fragment? = null

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter.prepareNavigationViewForUserStatus()

        mCurrentFragment = MainFragment.newInstance()
        prepareFragment(R.id.main_fragment_container, mCurrentFragment!!)
        setToolbarTitle(getString(R.string.main_item_title))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null

        when (item.itemId) {

            // common menu items

            R.id.navigation_item_main -> {
                selectedFragment = MainFragment.newInstance()
            }
            R.id.navigation_item_categories -> {
                selectedFragment = CategoriesFragment.newInstance(false)
            }

            // items for only signed in users

            R.id.navigation_item_favorites -> {
                if (userHaveAccessForMenuItem(item)) {
                    selectedFragment = FavoritesFragment.newInstance()
                } else {
                    return false
                }
            }
            R.id.navigation_item_recently_watched -> {
                if (userHaveAccessForMenuItem(item)) {
                    selectedFragment = RecentlyWatchedCompaniesFragment.newInstance(false)
                } else {
                    return false
                }
            }
            R.id.navigation_item_logout -> mPresenter.localUserLogout()

        }

        selectedFragment?.let {
           if (it.javaClass != mCurrentFragment?.javaClass) {
               mCurrentFragment = it
               prepareFragment(R.id.main_fragment_container, mCurrentFragment!!)
               setToolbarTitle(item.title.toString())
           }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SignInActivity.SIGN_IN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // invalidate navigation view
            mPresenter.prepareNavigationViewForUserStatus()
        }
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

        mLastAccessibleItem?.let {
            onNavigationItemSelected(it)
            // TODO check not work
            it.isChecked = true
        }
        mLastAccessibleItem = null

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
        mCurrentFragment = CompaniesFragment.newInstance(category)
        prepareFragment(R.id.main_fragment_container, mCurrentFragment!!)
    }

    override fun onCompanySelected(company: CompanyModel) {

    }

    override fun onShowMap(category: CategoryModel) {

    }

    override fun onShowCategoriesDialog() {
        val categoriesDialog = CategoriesFragment.newInstance(true)
        categoriesDialog.show(supportFragmentManager, "CategoriesFragment")
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
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.syncState()
        mDrawerLayout.addDrawerListener(toggle)

        mNavigationView.disableScrollbars()
        mNavigationView.setNavigationItemSelectedListener(this)
    }

    /**
     * Check user access for menu item
     *
     * If user have not access then show error dialog with confirm action
     * and save item for user redirection after successfully sign in
     */
    private fun userHaveAccessForMenuItem(item: MenuItem): Boolean {
        return if (mPresenter.isUserSignedIn())  {
            true
        } else {
            showError(R.string.access_denied_message) {
                // confirm dialog action
                startSignInActivityForResult()
            }
            // save item for user redirection
            mLastAccessibleItem = item
            false
        }
    }

    private fun startSignInActivityForResult() {
        val intent = Intent(getContext(), SignInActivity::class.java)
        startActivityForResult(intent, SignInActivity.SIGN_IN_REQUEST_CODE)
    }

    /* -------------------------------------- */

}