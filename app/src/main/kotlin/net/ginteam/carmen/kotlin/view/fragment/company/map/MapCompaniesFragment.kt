package net.ginteam.carmen.kotlin.view.fragment.company.map

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.AbsListView
import android.widget.Toast
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.animateCameraToLocation
import net.ginteam.carmen.kotlin.checkPermission
import net.ginteam.carmen.kotlin.contract.MapCompaniesFragmentContract
import net.ginteam.carmen.kotlin.getBounds
import net.ginteam.carmen.kotlin.interfaces.Filterable
import net.ginteam.carmen.kotlin.listener.PageAdapterListener
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.presenter.company.map.MapCompaniesFragmentPresenter
import net.ginteam.carmen.kotlin.view.adapter.company.map.MapCompaniesAdapter
import net.ginteam.carmen.kotlin.view.fragment.company.BaseCompaniesFragment
import net.ginteam.carmen.view.activity.map.CompanyClusterRenderer
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListHorizontalItemDecorator

/**
 * Created by eugene_shcherbinock on 2/21/17.
 */

class MapCompaniesFragment
    : BaseCompaniesFragment <MapCompaniesAdapter, MapCompaniesFragmentContract.View, MapCompaniesFragmentContract.Presenter>(),
        MapCompaniesFragmentContract.View, OnMapReadyCallback, ClusterManager.OnClusterClickListener<CompanyModel>,
        ClusterManager.OnClusterItemClickListener<CompanyModel>, GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraIdleListener, Filterable {

    override var mPresenter: MapCompaniesFragmentContract.Presenter = MapCompaniesFragmentPresenter()

    private val mUiThreadHandler: Handler = Handler()

    private lateinit var mSnapAdapterHelper: SnapHelper
    override lateinit var mCompaniesAdapter: MapCompaniesAdapter

    private var isNeedFetchCompanies: Boolean = false

    private var mFilterQuery: String = ""
    private var mUserSelectedCompany: CompanyModel? = null

    private lateinit var mSelectedCategory: CategoryModel
    private lateinit var mStartGoogleMapPosition: LatLng

    private lateinit var mGoogleMapInstance: GoogleMap
    private lateinit var mGoogleMapClusterManager: ClusterManager <CompanyModel>

    private lateinit var mGoogleMapView: MapView
    private lateinit var mSearchView: View
    private lateinit var mFiltersView: View

    private var mShowFiltersActivityListener: OnShowFiltersActivityListener? = null

    companion object {
        private const val SELECTION_DELAY: Long = 500
        private const val CATEGORY_ARGUMENT = "category"
        private const val INIT_MAP_POSITION_ARGUMENT = "init_map_position"

        fun newInstance(category: CategoryModel, startPosition: LatLng): MapCompaniesFragment {
            val bundle = Bundle()
            bundle.putSerializable(CATEGORY_ARGUMENT, category)
            bundle.putParcelable(INIT_MAP_POSITION_ARGUMENT, startPosition)

            val instance: MapCompaniesFragment = MapCompaniesFragment()
            instance.arguments = bundle
            return instance
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mShowFiltersActivityListener = context as OnShowFiltersActivityListener
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mGoogleMapView.onCreate(savedInstanceState)
        mGoogleMapView.getMapAsync(this)
    }

    // Google Map listeners

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mGoogleMapView.onResume()
            mGoogleMapInstance = it

            updateGoogleMapDependencies()

            mUiThreadHandler.post {
                isNeedFetchCompanies = true
                mGoogleMapInstance.animateCameraToLocation(mStartGoogleMapPosition)
            }

            if (checkPermission()) {
                mGoogleMapInstance.isMyLocationEnabled = true
            }
        }
    }

    override fun onCameraIdle() {
        mGoogleMapClusterManager.onCameraIdle()
        showSearchView(true)

        if (isNeedFetchCompanies) {
            fetchCompanies()
            isNeedFetchCompanies = false
        }
    }

    override fun onCameraMoveStarted(p0: Int) {
        showSearchView(false)
    }

    override fun onClusterItemClick(company: CompanyModel?): Boolean {
        selectCompany(company, withScroll = true)
        return false
    }

    override fun onClusterClick(companyCluster: Cluster<CompanyModel>?): Boolean {
        return true
    }

    // -------------------

    override fun setFilterQuery(filter: String) {
        mGoogleMapInstance.clear()
        mFilterQuery = filter
        fetchCompanies()
    }

    override fun showCompanies(companies: MutableList<CompanyModel>, pagination: PaginationModel?) {
        mGoogleMapClusterManager.clearItems()
        mGoogleMapClusterManager.addItems(companies)
        mGoogleMapClusterManager.cluster()

        mCompaniesAdapter = MapCompaniesAdapter(companies, this, this)
        mRecyclerViewCompanies.adapter = mCompaniesAdapter

        mUiThreadHandler.postDelayed({
            selectCompany(mCompaniesAdapter.getCompany(0))
        }, SELECTION_DELAY)
    }

    override fun showSearchView(show: Boolean) {
        mSearchView.animate().alpha(if (show) 1f else 0f).start()
    }

    override fun showCompaniesView(show: Boolean) {
        mRecyclerViewCompanies
                .animate()
                .translationY(if (show) 0f else mRecyclerViewCompanies.height.toFloat())
                .setInterpolator(LinearInterpolator())
                .withEndAction {
                    mFiltersView.animate().alpha(if (show) 1f else 0f).start()
                }
                .start()
        mRecyclerViewCompanies.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun getNetworkErrorAction(): (() -> Unit)? = {
        fetchCompanies()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_companies_map

    override fun fetchCompanies() {
        mPresenter.fetchCompanies(mSelectedCategory, mFilterQuery, mGoogleMapInstance.getBounds())
    }

    override fun getRecyclerViewItemDecorator(): RecyclerView.ItemDecoration
            = CompanyRecyclerListHorizontalItemDecorator(context, R.dimen.company_item_spacing)

    override fun getRecyclerViewLayoutManager(): LinearLayoutManager
            = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mGoogleMapView = mFragmentView.findViewById(R.id.google_map) as MapView
        mSearchView = mFragmentView.findViewById(R.id.button_repeat_search)
        mFiltersView = mFragmentView.findViewById(R.id.button_filters)

        mSnapAdapterHelper = GravitySnapHelper(GravityCompat.START, false) { position ->
            selectCompany(mCompaniesAdapter.getCompany(position))
        }
        mSnapAdapterHelper.attachToRecyclerView(mRecyclerViewCompanies)

        mSearchView.setOnClickListener {
            mGoogleMapInstance.clear()
            fetchCompanies()
        }
        mFiltersView.setOnClickListener {
            mShowFiltersActivityListener?.onShowFiltersActivity(mSelectedCategory)
        }
    }

    private fun selectCompany(company: CompanyModel?, withScroll: Boolean = false) {
        val companyRenderer: CompanyClusterRenderer = mGoogleMapClusterManager.renderer as CompanyClusterRenderer

        // if we already have selected company
        mUserSelectedCompany?.let {
            // set selection to false
            it.isSelected = false

            // invalidate marker and adapter item
            companyRenderer.updateClusterItem(it)
            mCompaniesAdapter.updateCompanyItem(it)
        }

        // if we select company
        if (company?.isSelected == false) {

            // save it
            mUserSelectedCompany = company
            mUserSelectedCompany!!.isSelected = true

            // invalidate marker and adapter item
            companyRenderer.updateClusterItem(mUserSelectedCompany)
            val updatedPosition = mCompaniesAdapter.updateCompanyItem(mUserSelectedCompany!!)

            if (withScroll) {
                mRecyclerViewCompanies.scrollToPosition(updatedPosition)
            }

            // animate camera to marker position
            mGoogleMapInstance
                    .animateCameraToLocation(
                            mUserSelectedCompany!!.position,
                            mGoogleMapInstance.cameraPosition.zoom)
        }
    }

    override fun updateDependencies() {
        super.updateDependencies()

        mSelectedCategory = arguments.getSerializable(CATEGORY_ARGUMENT) as CategoryModel
        mStartGoogleMapPosition = arguments.getParcelable(INIT_MAP_POSITION_ARGUMENT)
    }

    private fun updateGoogleMapDependencies() {
        mGoogleMapClusterManager = ClusterManager(context, mGoogleMapInstance)
        mGoogleMapClusterManager.renderer = CompanyClusterRenderer(context, mGoogleMapInstance, mGoogleMapClusterManager)
        mGoogleMapClusterManager.setOnClusterClickListener(this)
        mGoogleMapClusterManager.setOnClusterItemClickListener(this)

        mGoogleMapInstance.setOnCameraIdleListener(this)
        mGoogleMapInstance.setOnCameraMoveStartedListener(this)
        mGoogleMapInstance.setOnMarkerClickListener(mGoogleMapClusterManager)
    }

    interface OnShowFiltersActivityListener {

        fun onShowFiltersActivity(category: CategoryModel)

    }
}