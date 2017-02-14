package net.ginteam.carmen.kotlin.presenter

import android.content.Intent
import net.ginteam.carmen.kotlin.contract.LocationContract
import net.ginteam.carmen.manager.ApiGoogleManager

/**
 * Created by eugene_shcherbinock on 2/14/17.
 */

abstract class BaseLocationPresenter <V : LocationContract.View> : BasePresenter <V>(),
        LocationContract.Presenter <V>, ApiGoogleManager.OnReceiveLocationListener {

    protected lateinit var mGoogleApiManager: ApiGoogleManager

    override fun fetchUserLocation() {
        mGoogleApiManager.getLastLocation(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mGoogleApiManager.onActivityResult(requestCode, resultCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mGoogleApiManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun attachView(view: V) {
        super.attachView(view)
        mGoogleApiManager = ApiGoogleManager(mView?.getActivity())
    }

}