package net.ginteam.carmen.kotlin.view.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.Constants
import net.ginteam.carmen.kotlin.common.notifications.FirebaseNotificationsReceiveService
import net.ginteam.carmen.kotlin.contract.MainActivityContract
import net.ginteam.carmen.kotlin.disableScrollbars
import net.ginteam.carmen.kotlin.interfaces.Filterable
import net.ginteam.carmen.kotlin.interfaces.Sortable
import net.ginteam.carmen.kotlin.isMenuItemFragment
import net.ginteam.carmen.kotlin.model.*
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.prepareFragment
import net.ginteam.carmen.kotlin.presenter.MainActivityPresenter
import net.ginteam.carmen.kotlin.view.activity.authentication.SignInActivity
import net.ginteam.carmen.kotlin.view.activity.company.CompanyDetailsActivity
import net.ginteam.carmen.kotlin.view.activity.costs.BaseCostDetailsActivity
import net.ginteam.carmen.kotlin.view.activity.costs.FuelDetailsActivity
import net.ginteam.carmen.kotlin.view.activity.filter.FiltersActivity
import net.ginteam.carmen.kotlin.view.activity.map.MapActivity
import net.ginteam.carmen.kotlin.view.activity.news.NewsDetailsActivity
import net.ginteam.carmen.kotlin.view.activity.news.PopularNewsActivity
import net.ginteam.carmen.kotlin.view.fragment.MainFragment
import net.ginteam.carmen.kotlin.view.fragment.WebViewFragment
import net.ginteam.carmen.kotlin.view.fragment.category.CategoriesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.BaseCompaniesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.CompaniesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.FavoritesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.RecentlyWatchedCompaniesFragment
import net.ginteam.carmen.kotlin.view.fragment.cost.CostHistoryListFragment
import net.ginteam.carmen.kotlin.view.fragment.news.BaseNewsFragment
import net.ginteam.carmen.kotlin.view.fragment.news.MainNewsFragment
import net.ginteam.carmen.kotlin.view.fragment.sort.SortOptionsDialogFragment
import net.ginteam.carmen.utils.DeviceUtils
import net.ginteam.carmen.view.custom.ToolbarDrawerToggle
import net.ginteam.carmenfloatbutton.FloatingActionButton
import net.ginteam.carmenfloatbutton.FloatingActionMenu

class MainActivity : BaseActivity <MainActivityContract.View, MainActivityContract.Presenter>(),
        MainActivityContract.View, NavigationView.OnNavigationItemSelectedListener,
        CategoriesFragment.OnCategorySelectedListener, BaseCompaniesFragment.OnCompanySelectedListener,
        CompaniesFragment.OnBottomMenuItemSelectedListener, SortOptionsDialogFragment.OnSortOptionSelectedListener,
        BaseNewsFragment.OnNewsItemSelectedListener, CostHistoryListFragment.OnHistoryItemSelectedListener,
        FloatingActionMenu.OnMenuItemClickListener{

    override var mPresenter: MainActivityContract.Presenter = MainActivityPresenter()

    private var mIntentNotification: NotificationModel? = null

    private var mLastAccessibleItem: MenuItem? = null
    private var mPreviousTitle: Pair <String, String>? = null
    private var mPreviousFragment: Fragment? = null

    private lateinit var mCosts: List<CostTypeModel>

    private lateinit var mCurrentFragment: Fragment

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private lateinit var mFloatingActionMenu: net.ginteam.carmenfloatbutton.FloatingActionMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.fetchCosts()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.prepareNavigationViewByUserStatus()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null

        when (item.itemId) {

        // common menu items

            R.id.navigation_item_main -> selectedFragment = MainFragment.newInstance()
            R.id.navigation_item_categories -> selectedFragment = CategoriesFragment.newInstance(false)
            R.id.navigation_item_privacy_policy -> selectedFragment = WebViewFragment.newInstance(Constants.PRIVACY_POLICY_URL)
            R.id.navigation_item_news -> selectedFragment = MainNewsFragment.newInstance()
            R.id.navigation_item_cost_history -> selectedFragment = CostHistoryListFragment.newInstance()

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
            if (it.javaClass != mCurrentFragment.javaClass) {
                mCurrentFragment = it
                prepareFragment(R.id.main_fragment_container, mCurrentFragment)

                setToolbarTitle(item.title.toString())
                mPreviousTitle = Pair(getToolbarTitle(), "")
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SignInActivity.SIGN_IN_REQUEST_CODE -> {
                    // invalidate navigation view
//                    mPresenter.prepareNavigationViewByUserStatus()
                }
                FiltersActivity.FILTER_CONFIRM_REQUEST_CODE -> {
                    (mCurrentFragment as? Filterable)?.setFilterQuery(
                            data!!.getStringExtra(FiltersActivity.RESULT_FILTER_ARGUMENT)
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
            return
        }

        if (MainFragment::class.java == mCurrentFragment.javaClass) {
            super.onBackPressed()
            return
        }

        if (mCurrentFragment.isMenuItemFragment()) {
            mCurrentFragment = MainFragment.newInstance()
            prepareFragment(R.id.main_fragment_container, mCurrentFragment)
            setToolbarTitle(getString(R.string.main_item_title))
            return
        }

        if (mPreviousFragment != null) {
            mCurrentFragment = mPreviousFragment!!
            prepareFragment(R.id.main_fragment_container, mCurrentFragment)

            setToolbarTitle(mPreviousTitle!!.first)
            setToolbarSubtitle(mPreviousTitle!!.second)

            mPreviousFragment = null
            mPreviousTitle = null
            return
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    /* MainActivityContract.View implementation */

    override fun inflateNavigationView(menuResId: Int, headerLayoutResId: Int) {
        mNavigationView.menu.clear()
        mNavigationView.removeHeaderView(mNavigationView.getHeaderView(0))

        mNavigationView.inflateMenu(menuResId)
        mNavigationView.inflateHeaderView(headerLayoutResId)

        mLastAccessibleItem?.let { onNavigationItemSelected(it) }
        mLastAccessibleItem = null

        // if user does not signed in
        if (headerLayoutResId == R.layout.navigation_view_default_header) {
            mNavigationView.getHeaderView(0).findViewById(R.id.button_sign_in).setOnClickListener {
                showSignInActivity()
            }
        }
    }

    override fun showCosts(costs: List<CostTypeModel>) {
        mCosts = costs
        mFloatingActionMenu = findViewById(R.id.floating_action_menu) as FloatingActionMenu
        mFloatingActionMenu.visibility = View.VISIBLE
        mFloatingActionMenu.onMenuItemClickListener = this

        createFloatingMenu(costs)
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
        if (!fromDialogSelection) {
            mPreviousFragment = mCurrentFragment
            mPreviousTitle = Pair(getToolbarTitle(), getToolbarSubtitle())
        }

        mCurrentFragment = CompaniesFragment.newInstance(category)
        prepareFragment(R.id.main_fragment_container, mCurrentFragment)

        setToolbarTitle(category.name)
        setToolbarSubtitle(mPresenter.getUserCityName())
    }

    override fun onCompanySelected(company: CompanyModel) {
        val intent = Intent(getContext(), CompanyDetailsActivity::class.java)
        intent.putExtra(CompanyDetailsActivity.COMPANY_ARGUMENT, company)

        if (DeviceUtils.hasLollipop()) {
            company.transitionViewHolder?.let {
                val companyPhotoPair: Pair <View, String> = Pair(it.mImageViewPhoto, getString(R.string.transition_company_photo))
//                val companyNamePair: Pair <View, String> = Pair(it.mTextViewName, getString(R.string.transition_company_name))
//                val companyRatingPair: Pair <View, String> = Pair(it.mRatingViewRating, getString(R.string.transition_company_rating))

                val transitionOptions: ActivityOptionsCompat
                        = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this, companyPhotoPair)
                startActivity(intent, transitionOptions.toBundle())
            }
            return
        }
        startActivity(intent)
    }

    override fun onNewsItemSelected(newsItem: NewsModel) {
        val intent = Intent(getContext(), NewsDetailsActivity::class.java)
        intent.putExtra(NewsDetailsActivity.NEWS_ARGUMENT, newsItem)

        if (DeviceUtils.hasLollipop()) {
            newsItem.transitionViewHolder?.let {
                val newsPhotoPair: Pair <View, String> = Pair(it.mImageViewNewsPhoto, getString(R.string.transition_news_photo))

                val transitionOptions: ActivityOptionsCompat
                        = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this, newsPhotoPair)
                startActivity(intent, transitionOptions.toBundle())
            }
            return
        }
        startActivity(intent)
    }

    override fun onShowMap(category: CategoryModel) {
        val intent = Intent(getContext(), MapActivity::class.java)
        intent.putExtra(MapActivity.CATEGORY_ARGUMENT, category)
        startActivity(intent)
    }

    override fun onShowCategoriesDialog() {
        val categoriesDialog = CategoriesFragment.newInstance(true)
        categoriesDialog.show(supportFragmentManager, "CategoriesFragment")
    }

    override fun onShowFiltersActivity(category: CategoryModel) {
        startFiltersActivityForResult(category)
    }

    override fun onShowSortDialog(category: CategoryModel) {
        val sortDialog = SortOptionsDialogFragment.newInstance(category)
        sortDialog.show(supportFragmentManager, "SortOptionsDialogFragment")
    }

    override fun onSortOptionSelected(field: String, type: String) {
        (mCurrentFragment as? Sortable)?.setSortQuery(field, type)
    }

    override fun onHistoryItemSelected(historyItem: HistoryModel) {
        val intent = Intent(getContext(), FuelDetailsActivity::class.java)
        intent.putExtra(BaseCostDetailsActivity.HISTORY_ID_ARGUMENT, historyItem.id)
        startActivity(intent)
    }

    override fun onMenuItemClick(fam: FloatingActionMenu?, index: Int, item: FloatingActionButton?) {
        val intent = Intent(getContext(), FuelDetailsActivity::class.java)
        intent.putExtra(BaseCostDetailsActivity.COST_ID_ARGUMENT, mCosts[index].id)
        startActivity(intent)
    }

    //    override fun onRFACItemLabelClick(position: Int, item: RFACLabelItem<CostTypeModel>?) {
//        onRFACItemIconClick(position, item)
//    }
//
//    override fun onRFACItemIconClick(position: Int, item: RFACLabelItem<CostTypeModel>?) {
//        val intent = Intent(getContext(), FuelDetailsActivity::class.java)
//        intent.putExtra(BaseCostDetailsActivity.COST_ID_ARGUMENT, item!!.wrapper.id)
//        startActivity(intent)
//        mFloatButtonHelper.toggleContent()
//    }

    /* -------------------------------------- */

    /* Private */

    override fun updateDependencies() {
        super.updateDependencies()

        mIntentNotification = intent
                .getSerializableExtra(FirebaseNotificationsReceiveService.NOTIFICATION_ARGUMENT) as NotificationModel?
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mCurrentFragment = MainFragment.newInstance()
        prepareFragment(R.id.main_fragment_container, mCurrentFragment)
        setToolbarTitle(getString(R.string.main_item_title))

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

        // if start from notification intent then select news item
        mIntentNotification?.let {
            val newsMenuItem = mNavigationView.menu.findItem(R.id.navigation_item_news)
            onNavigationItemSelected(newsMenuItem)

            startPopularNewsActivity(it)
        }
    }

    private fun createFloatingMenu(costs: List<CostTypeModel>) {
        costs.forEach {
            val floatingActionButton: FloatingActionButton = FloatingActionButton(getContext())
            floatingActionButton.colorNormal = Color.parseColor(it.color)
            floatingActionButton.colorPressed = Color.parseColor(it.color)
            floatingActionButton.labelText = it.name
            floatingActionButton.buttonSize = FloatingActionButton.SIZE_MINI
            var drawable: Drawable = ContextCompat.getDrawable(this, R.drawable.ic_carwash_float_button)
            when (costs.indexOf(it)) {
                0 -> {drawable = ContextCompat.getDrawable(this, R.drawable.ic_refuelling_float_button)}
                1 -> {drawable = ContextCompat.getDrawable(this, R.drawable.ic_carwash_float_button)}
                2 -> {drawable = ContextCompat.getDrawable(this, R.drawable.ic_car_service_float_button)}
                3 -> {drawable = ContextCompat.getDrawable(this, R.drawable.ic_costs_float_button)}
            }
            floatingActionButton.setImageDrawable(drawable)
            mFloatingActionMenu.addMenuButton(floatingActionButton)
        }
    }

    /**
     * Check user access for menu item
     *
     * If user have not access then show error dialog with confirm action
     * and save item for user redirection after successfully sign in
     */
    private fun userHaveAccessForMenuItem(item: MenuItem): Boolean {
        return if (mPresenter.isUserSignedIn()) {
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

    private fun startFiltersActivityForResult(category: CategoryModel) {
        val intent = Intent(getContext(), FiltersActivity::class.java)
        intent.putExtra(FiltersActivity.CATEGORY_ARGUMENT, category)
        startActivityForResult(intent, FiltersActivity.FILTER_CONFIRM_REQUEST_CODE)
    }

    private fun startPopularNewsActivity(notification: NotificationModel) {
        val intent = Intent(getContext(), PopularNewsActivity::class.java)
        intent.putExtra(FirebaseNotificationsReceiveService.NOTIFICATION_ARGUMENT, notification)
        startActivity(intent)
    }

    /* -------------------------------------- */

}