package net.ginteam.carmen.kotlin.view.fragment.company.map

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.maps.android.clustering.ClusterManager
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.animateCameraToLocation
import net.ginteam.carmen.kotlin.contract.MapCompanyFragmentContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.presenter.company.map.MapCompanyFragmentPresenter
import net.ginteam.carmen.kotlin.view.fragment.BaseFragment
import net.ginteam.carmen.view.activity.map.CompanyClusterRenderer

/**
 * Created by eugene_shcherbinock on 2/23/17.
 */

class MapCompanyFragment : BaseFragment <MapCompanyFragmentContract.View, MapCompanyFragmentContract.Presenter>(),
        MapCompanyFragmentContract.View, OnMapReadyCallback {

    override var mPresenter: MapCompanyFragmentContract.Presenter = MapCompanyFragmentPresenter()

    private lateinit var mSelectedCompany: CompanyModel

    private val mUiThreadHandler: Handler = Handler()

    private lateinit var mGoogleMapView: MapView
    private lateinit var mGoogleMapInstance: GoogleMap
    private lateinit var mGoogleMapClusterManager: ClusterManager<CompanyModel>

    companion object {
        private const val COMPANY_ARGUMENT = "company"

        fun newInstance(company: CompanyModel): MapCompanyFragment {
            val bundle = Bundle()
            bundle.putSerializable(COMPANY_ARGUMENT, company)

            val instance: MapCompanyFragment = MapCompanyFragment()
            instance.arguments = bundle
            return instance
        }
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
            mGoogleMapInstance.uiSettings.setAllGesturesEnabled(false)

            updateGoogleMapDependencies()

            mUiThreadHandler.post {
                mGoogleMapInstance.animateCameraToLocation(mSelectedCompany.position)
            }
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_company_map

    override fun updateViewDependencies() {
        super.updateViewDependencies()
        mGoogleMapView = mFragmentView.findViewById(R.id.google_map) as MapView
    }

    override fun updateDependencies() {
        super.updateDependencies()
        mSelectedCompany = arguments.getSerializable(COMPANY_ARGUMENT) as CompanyModel
    }

    private fun updateGoogleMapDependencies() {
        mGoogleMapClusterManager = ClusterManager(context, mGoogleMapInstance)
        mGoogleMapClusterManager.renderer = CompanyClusterRenderer(context, mGoogleMapInstance, mGoogleMapClusterManager)

        mGoogleMapClusterManager.addItem(mSelectedCompany)
        mGoogleMapClusterManager.cluster()
    }
}