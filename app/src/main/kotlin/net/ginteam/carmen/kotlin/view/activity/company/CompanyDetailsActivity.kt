package net.ginteam.carmen.kotlin.view.activity.company

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.CompanyDetailsContract
import net.ginteam.carmen.kotlin.model.*
import net.ginteam.carmen.kotlin.prepareFragment
import net.ginteam.carmen.kotlin.presenter.company.detail.CompanyDetailsPresenter
import net.ginteam.carmen.kotlin.view.activity.BaseActivity
import net.ginteam.carmen.kotlin.view.activity.authentication.SignInActivity
import net.ginteam.carmen.kotlin.view.activity.map.MapActivity
import net.ginteam.carmen.kotlin.view.fragment.company.BaseCompaniesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.PopularCompaniesFragment
import net.ginteam.carmen.utils.DeviceUtils
import net.ginteam.carmen.view.adapter.company.GalleryRecyclerAdapter
import net.ginteam.carmen.view.custom.rating.CarmenRatingView
import net.ginteam.carmen.view.fragment.company.AdditionalServicesFragment
import net.ginteam.carmen.view.fragment.company.ReviewsFragment
import net.ginteam.carmen.view.fragment.company.ServiceCategoryListFragment

/**
 * Created by eugene_shcherbinock on 2/26/17.
 */

class CompanyDetailsActivity : BaseActivity<CompanyDetailsContract.View, CompanyDetailsContract.Presenter>(),
        CompanyDetailsContract.View, BaseCompaniesFragment.OnCompanySelectedListener {

    override var mPresenter: CompanyDetailsContract.Presenter = CompanyDetailsPresenter()

    private lateinit var mSelectedCompany: CompanyModel

    private var mUserRating: Float = 0f
    private var isRecyclerViewAttached: Boolean = false

    private var mOptionsMenu: Menu? = null
    private lateinit var mRatingViewUser: CarmenRatingView

    private var mIndicatorsCount: Int = 0
    private lateinit var mIndicators: Array <ImageView>

    companion object {
        const val COMPANY_ARGUMENT = "company"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (DeviceUtils.hasLollipop()) {
            postponeEnterTransition()
        }

        mPresenter.fetchCompanyDetail(mSelectedCompany)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            mOptionsMenu = it
            menuInflater.inflate(R.menu.company_detail_menu, mOptionsMenu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_favorite -> {
                if (userHaveAccess()) {
                    if (mSelectedCompany.isFavorite) {
                        mPresenter.removeCompanyFromFavorites(mSelectedCompany)
                    } else {
                        mPresenter.addCompanyToFavorites(mSelectedCompany)
                    }
                }
            }
            else -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SignInActivity.SIGN_IN_REQUEST_CODE -> mPresenter.fetchCompanyDetail(mSelectedCompany)
            }
        }
    }

    override fun onCompanySelected(company: CompanyModel) {
        val intent = Intent(getContext(), CompanyDetailsActivity::class.java)
        intent.putExtra(CompanyDetailsActivity.COMPANY_ARGUMENT, company)

        if (DeviceUtils.hasLollipop()) {
            company.transitionViewHolder?.let {
                val companyPhotoPair: Pair<View, String> = Pair(it.mImageViewPhoto, getString(R.string.transition_company_photo))
//                val companyNamePair: Pair<View, String> = Pair(it.mTextViewName, getString(R.string.transition_company_name))
//                val companyRatingPair: Pair<View, String> = Pair(it.mRatingViewRating, getString(R.string.transition_company_rating))

                val transitionOptions: ActivityOptionsCompat
                        = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this, companyPhotoPair)
                startActivity(intent, transitionOptions.toBundle())
            }
            return
        }
        startActivity(intent)
    }

    override fun showLoading(show: Boolean, messageResId: Int) {
        super.showLoading(show, messageResId)
        findViewById(R.id.layout_content).visibility = if (show) View.GONE else View.VISIBLE
    }

    override fun getLayoutResId(): Int = R.layout.activity_company_detail

    /*
        CompanyDetailsContract.View implementation
     */
    override fun showCompanyDetails(company: CompanyModel) {
        if (DeviceUtils.hasLollipop()) {
            startPostponedEnterTransition()
        }

        mSelectedCompany = company
        invalidateFavoriteIndicator(company.isFavorite)

        if (!isRecyclerViewAttached) {
            showCompanyPhotos(company.pictures)
        }
        loadStaticGoogleMapImage(company.position)
        setUserRatingIfExists(company.userRatings)
        showMainCompanyInformation(company)
    }

    override fun invalidateFavoriteIndicator(isFavorite: Boolean) {
        val drawableId: Int = if (isFavorite) {
            R.drawable.ic_company_favorite_enable
        } else {
            R.drawable.ic_company_favorite_disable
        }
        mOptionsMenu?.getItem(0)?.icon = ContextCompat.getDrawable(getContext(), drawableId)
    }

    override fun showUpdateRatingActivity(rating: RatingModel) {
        val intent = Intent(getContext(), CompanyRatingUpdateActivity::class.java)
        intent.putExtra(CompanyRatingUpdateActivity.COMPANY_ARGUMENT, mSelectedCompany)
        intent.putExtra(CompanyRatingUpdateActivity.RATING_ARGUMENT, rating)
        startActivity(intent)
    }

    override fun showPopularCompanies() {
        prepareFragment(R.id.popular_companies_fragment_container, PopularCompaniesFragment.newInstance(true))
    }

    override fun showComforts(comforts: List<ComfortModel>) {
        prepareFragment(R.id.additional_services_fragment_container, AdditionalServicesFragment.newInstance(comforts))
    }

    override fun showRatings(ratings: List<RatingModel>) {
        prepareFragment(R.id.reviews_fragment_container, ReviewsFragment.newInstance(ratings))
    }

    override fun showCategoryServices(categories: List<CategoryModel>) {
        prepareFragment(R.id.service_category_fragment_container, ServiceCategoryListFragment.newInstance(categories))
    }

    /*
        Private
     */

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_button)

        mRatingViewUser = findViewById(R.id.rating_view_vote_object) as CarmenRatingView
        mRatingViewUser.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (!fromUser) return@setOnRatingBarChangeListener

            if (mUserRating != 0f) {
                ratingBar.rating = mUserRating
                showMessage(R.string.error_vote_object)
                return@setOnRatingBarChangeListener
            }

            if (!userHaveAccess()) {
                ratingBar.rating = 0f
                return@setOnRatingBarChangeListener
            }

            mPresenter.createRating(mSelectedCompany, rating)
        }

        findViewById(R.id.button_show_on_map).setOnClickListener { showCompanyOnMap() }
        findViewById(R.id.button_open_navigate).setOnClickListener { startNavigationApplication() }

        invalidateOptionsMenu()
    }

    override fun updateDependencies() {
        super.updateDependencies()
        mSelectedCompany = intent.getSerializableExtra(COMPANY_ARGUMENT) as CompanyModel
    }

    private fun showCompanyPhotos(imagesUrls: List <String>?) {
        imagesUrls?.let {
            val recyclerViewGallery: RecyclerView = findViewById(R.id.recycler_view_gallery) as RecyclerView
            val galleryAdapter: GalleryRecyclerAdapter = GalleryRecyclerAdapter(getContext(), imagesUrls)
            val layoutManager: LinearLayoutManager = LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
            val gallerySnapHelper: GravitySnapHelper = GravitySnapHelper(Gravity.START, true) {
                position ->
                setIndicator(position)
            }

            recyclerViewGallery.layoutManager = layoutManager
            recyclerViewGallery.adapter = galleryAdapter
            gallerySnapHelper.attachToRecyclerView(recyclerViewGallery)

            setupGalleryIndicator(galleryAdapter)
            isRecyclerViewAttached = true
        }
    }

    private fun showMainCompanyInformation(company: CompanyModel) {
        (findViewById(R.id.text_view_company_name) as TextView).text = company.name
        (findViewById(R.id.text_view_company_address) as TextView).text = company.address
        (findViewById(R.id.rating_view_company_price) as CarmenRatingView).rating = company.price.toFloat()
        (findViewById(R.id.rating_view_company) as CarmenRatingView).rating = company.rating.toFloat()
        (findViewById(R.id.text_view_company_distance) as TextView).text = if (company.distance == 0.0) {
            String.format("%s %s", "?", getString(R.string.location_measure))
        } else {
            String.format("%.1f %s", company.distance / 1000, getString(R.string.location_measure))
        }

        company.categories?.data?.let {
            val mainCategoriesNamesList: List <String>
                    = it.filter { it.parentId == 0 }.map { it.name }
            (findViewById(R.id.text_view_category) as TextView).text = mainCategoriesNamesList.joinToString(", ")
        }
        company.details?.data?.let {
            (findViewById(R.id.text_view_company_work_time) as TextView).text = String.format(
                    getString(R.string.work_time_text), it.closedTime ?: "24:00")

            it.phones?.let {
                if (it.isNotEmpty()) {
                    val callButton = findViewById(R.id.action_button_call) as ImageButton
                    callButton.visibility = View.VISIBLE
                    callButton.setOnClickListener { startCallActivity(company.details.data.phones!!) }
                }
            }
        }
        company.ratings?.data?.let {
            (findViewById(R.id.text_view_review_count) as TextView).text = resources
                    .getQuantityString(R.plurals.review_count_string, it.size, it.size)
        }
    }

    private fun loadStaticGoogleMapImage(companyPosition: LatLng) {
        val googleMapImageUrl: String = String.format("http://maps.googleapis.com/maps/api/staticmap?center=%1\$s," +
                "%2\$s&markers=icon:http://carmen.b4u.com.ua/assets/icons/11-mdpi.png|" +
                "%1\$s,%2\$s&zoom=17&scale=1&" +
                "size=%3\$sx%4\$s&format=png&visual_refresh=true",
                companyPosition.latitude, companyPosition.longitude, 640, 480)

        Picasso
                .with(getContext())
                .load(googleMapImageUrl)
                .into(findViewById(R.id.image_view_map) as ImageView)
    }

    private fun setUserRatingIfExists(userRating: ResponseModel<List<RatingModel>>?) {
        userRating?.data?.let {
            if (it.isNotEmpty()) {
                val rating: Float = it.first().totalRating
                mRatingViewUser.rating = rating
                mUserRating = rating
            }
        }
    }

    private fun showCompanyOnMap() {
        val intent = Intent(getContext(), MapActivity::class.java)
        intent.putExtra(MapActivity.COMPANY_ARGUMENT, mSelectedCompany)
        startActivity(intent)
    }

    private fun startCallActivity(companyPhones: List <String>) {
        val intent = Intent(Intent.ACTION_DIAL)
        if (companyPhones.size > 1) {
            showPhonesSelectionDialog(companyPhones) {
                intent.data = Uri.parse("tel: $it")
                startActivity(intent)
            }
        } else {
            intent.data = Uri.parse("tel:" + companyPhones.first())
            startActivity(intent)
        }
    }

    private fun showPhonesSelectionDialog(companyPhones: List <String>, phoneSelectionListener: (String) -> Unit) {
        val phonesAdapter: ArrayAdapter<String> = ArrayAdapter(getContext(), android.R.layout.select_dialog_item)
        phonesAdapter.addAll(companyPhones)

        AlertDialog.Builder(this)
                .setTitle(R.string.choose_company_phone)
                .setAdapter(phonesAdapter) { _, which ->
                    phoneSelectionListener(phonesAdapter.getItem(which))
                }.show()
    }

    private fun startNavigationApplication() {
        val googlePackageName: String = "com.google.android.apps.maps"
        val uri: String = String.format(
                "geo:%1\$s,%2\$s?q=%1\$s,%2\$s",
                mSelectedCompany.position.latitude,
                mSelectedCompany.position.longitude)

        val navigationApplicationUri: Uri = Uri.parse(uri)
        val googleMapApplicationIntent = Intent(Intent.ACTION_VIEW, navigationApplicationUri)
        googleMapApplicationIntent.`package` = googlePackageName

        try {
            startActivity(googleMapApplicationIntent)
        } catch (e: ActivityNotFoundException) {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + googlePackageName)))
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + googlePackageName)))
            }
        }
    }

    private fun setupGalleryIndicator(adapter: GalleryRecyclerAdapter) {
        mIndicatorsCount = adapter.itemCount
        if (mIndicatorsCount > 1) {
            mIndicators = Array(mIndicatorsCount) { ImageView(getContext()) }
            for (i in 0..mIndicatorsCount - 1) {
                mIndicators[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonselecteditem_dot))
                val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(5, 0, 5, 0)
                (findViewById(R.id.gallery_indicator) as LinearLayout).addView(mIndicators[i], params)
            }
            mIndicators[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selecteditem_dot))
        }
    }

    private fun setIndicator(position: Int) {
        if (mIndicatorsCount > 1) {
            for (i in 0..mIndicatorsCount - 1) {
                mIndicators[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonselecteditem_dot))
            }
            mIndicators[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selecteditem_dot))
        }
    }

    private fun userHaveAccess(): Boolean {
        return if (mPresenter.isUserSignedIn()) {
            true
        } else {
            showError(R.string.access_denied_message) {
                // confirm dialog action
                startSignInActivityForResult()
            }
            false
        }
    }

    private fun startSignInActivityForResult() {
        val intent = Intent(getContext(), SignInActivity::class.java)
        startActivityForResult(intent, SignInActivity.SIGN_IN_REQUEST_CODE)
    }

}