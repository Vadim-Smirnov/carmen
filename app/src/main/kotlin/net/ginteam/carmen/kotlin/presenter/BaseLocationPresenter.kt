package net.ginteam.carmen.kotlin.presenter

import android.content.Intent
import net.ginteam.carmen.kotlin.contract.LocationContract
import net.ginteam.carmen.manager.GoogleLocationManager

/**
 * Created by eugene_shcherbinock on 2/14/17.
 */

abstract class BaseLocationPresenter<V : LocationContract.View> : BasePresenter <V>(),
        LocationContract.Presenter <V>, GoogleLocationManager.OnReceiveLocationListener {

    protected lateinit var mGoogleLocationManager: GoogleLocationManager

    override fun fetchUserLocation() {
        mGoogleLocationManager.getLastLocation(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mGoogleLocationManager.onActivityResult(requestCode, resultCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mGoogleLocationManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun attachView(view: V) {
        super.attachView(view)
        mGoogleLocationManager = GoogleLocationManager(mView?.getActivity())
    }

}