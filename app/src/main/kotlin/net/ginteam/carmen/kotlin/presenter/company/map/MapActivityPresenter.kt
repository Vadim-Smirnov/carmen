package net.ginteam.carmen.kotlin.presenter.company.map

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import net.ginteam.carmen.kotlin.contract.FiltersContract
import net.ginteam.carmen.kotlin.contract.MapActivityContract
import net.ginteam.carmen.kotlin.manager.PreferencesManager
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.PointModel
import net.ginteam.carmen.kotlin.presenter.BaseLocationPresenter
import net.ginteam.carmen.kotlin.view.activity.filter.FiltersActivityViewState

/**
 * Created by eugene_shcherbinock on 2/21/17.
 */

class MapActivityPresenter : BaseLocationPresenter <MapActivityContract.View>(), MapActivityContract.Presenter {

    override fun fetchUserLocation() {
        super.fetchUserLocation()
        mView?.showLoading(true)
    }

    override fun onLocationReceived(location: Location?) {
        mView?.showLoading(false)

        // show fragment with user position
        val userLocation: LatLng = LatLng(location!!.latitude, location.longitude)
        mView?.showMapFragment(userLocation)
    }

    override fun onLocationReceiveFailure() {
        mView?.showLoading(false)

        // show fragment with user city position
        val preferences: PreferencesManager = SharedPreferencesManager
        val userCityPoints: PointModel = preferences.userCityModel!!.point

        val userCityLocation: LatLng = LatLng(userCityPoints.latitude, userCityPoints.longitude)
        mView?.showMapFragment(userCityLocation)
    }

    override fun attachView(view: MapActivityContract.View) {
        super.attachView(view)

        val filtersViewState: FiltersContract.ViewState = FiltersActivityViewState
        filtersViewState.resetViewState()
    }
}