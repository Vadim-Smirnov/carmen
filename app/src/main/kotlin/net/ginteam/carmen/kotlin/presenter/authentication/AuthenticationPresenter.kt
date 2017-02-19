package net.ginteam.carmen.kotlin.presenter.authentication

import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.AuthContract
import net.ginteam.carmen.kotlin.manager.PreferencesManager
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.CityModel
import net.ginteam.carmen.kotlin.model.UserModel
import net.ginteam.carmen.kotlin.presenter.BaseLocationPresenter
import net.ginteam.carmen.kotlin.provider.AuthProvider
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider
import net.ginteam.carmen.kotlin.provider.CitiesDataSourceProvider
import net.ginteam.carmen.kotlin.provider.OnlineCitiesDataSourceProvider

/**
 * Created by eugene_shcherbinock on 2/14/17.
 */
class AuthenticationPresenter : BaseLocationPresenter <AuthContract.View>(), AuthContract.Presenter {

    private val mPreferences: PreferencesManager = SharedPreferencesManager

    override fun checkUserStatus() {
        val authProvider: AuthProvider = AuthenticationProvider
        authProvider
                .fetchCurrentUser()
                .subscribe(object : ModelSubscriber <UserModel>() {
                    override fun success(model: UserModel) {
                        mView?.showMainActivity()
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showSignInActivity()
                    }
                })
    }

    override fun onLocationReceived(location: Location?) {
        location?.let {
            val latLng = LatLng(location.latitude, location.longitude)
            mPreferences.userLocation = latLng

            fetchCity(object : ModelSubscriber <CityModel>() {
                override fun success(model: CityModel) {
                    mPreferences.userCityModel = model
                    checkUserStatus()
                }

                override fun error(message: String, isNetworkError: Boolean) {
                    mView?.showCitiesDialog()
                }
            })
        }
    }

    override fun onLocationReceiveFailure() {
        if (mPreferences.userCityModel == null) {
            mView?.showCitiesDialog()
            mPreferences.userLocation = null
            return
        }
        checkUserStatus()
    }

    private fun fetchCity(subscriber: ModelSubscriber<CityModel>) {
        val citiesProvider: CitiesDataSourceProvider = OnlineCitiesDataSourceProvider()
        citiesProvider
                .fetchUserCity()
                .subscribe(subscriber)
    }

}