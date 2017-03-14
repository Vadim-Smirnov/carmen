package net.ginteam.carmen.kotlin.view.activity

import android.content.Intent
import net.ginteam.carmen.kotlin.common.notifications.FirebaseNotificationsReceiveService
import net.ginteam.carmen.kotlin.contract.AuthContract
import net.ginteam.carmen.kotlin.model.CityModel
import net.ginteam.carmen.kotlin.model.NotificationModel
import net.ginteam.carmen.kotlin.presenter.authentication.AuthenticationPresenter
import net.ginteam.carmen.kotlin.view.activity.authentication.SignInActivity
import net.ginteam.carmen.kotlin.view.fragment.city.CitiesDialogFragment

class SplashActivity : BaseLocationActivity<AuthContract.View, AuthContract.Presenter>(),
        AuthContract.View, CitiesDialogFragment.OnCitySelectedListener {

    override var mPresenter: AuthContract.Presenter = AuthenticationPresenter()

    override fun onStart() {
        super.onStart()
        mPresenter.fetchUserLocation()
    }

    override fun getLayoutResId(): Int = 0

    override fun showCitiesDialog() {
        super.onPostResume()

        val citiesDialog = CitiesDialogFragment.newInstance()
        citiesDialog.show(supportFragmentManager, "")
    }

    override fun onCitySelected(city: CityModel) {
        mPresenter.checkUserStatus()
    }

    override fun authorizationConfirmed() {
        val intent = Intent(getContext(), MainActivity::class.java)
        // put notification in intent if exists
        tryToGetNotificationFromIntent()?.let {
            intent.putExtra(FirebaseNotificationsReceiveService.NOTIFICATION_ARGUMENT, it)
            mPresenter.updateNotificationStatus(it)
        }
        startActivity(intent)
        finish()
    }

    override fun showSignInActivity() {
        val intent = Intent(getContext(), SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun tryToGetNotificationFromIntent(): NotificationModel?
            = intent.getSerializableExtra(FirebaseNotificationsReceiveService.NOTIFICATION_ARGUMENT) as NotificationModel?

}
