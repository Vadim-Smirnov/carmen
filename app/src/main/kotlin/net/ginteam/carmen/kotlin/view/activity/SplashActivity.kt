package net.ginteam.carmen.kotlin.view.activity

import android.content.Intent
import android.os.Bundle
import net.ginteam.carmen.kotlin.contract.AuthContract
import net.ginteam.carmen.kotlin.model.CityModel
import net.ginteam.carmen.kotlin.presenter.authentication.AuthenticationPresenter
import net.ginteam.carmen.kotlin.view.activity.authentication.SignInActivity
import net.ginteam.carmen.kotlin.view.fragment.city.CitiesDialogFragment

class SplashActivity : BaseLocationActivity<AuthContract.View, AuthContract.Presenter>(),
        AuthContract.View, CitiesDialogFragment.OnCitySelectedListener {

    override var mPresenter: AuthContract.Presenter = AuthenticationPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.fetchUserLocation()
    }

    override fun getLayoutResId(): Int = 0

    override fun showCitiesDialog() {
        val citiesDialog = CitiesDialogFragment.newInstance()
        citiesDialog.show(supportFragmentManager, "")
    }

    override fun onCitySelected(city: CityModel) {
        mPresenter.checkUserStatus()
    }

    override fun showMainActivity() {
        val intent = Intent(getContext(), MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showSignInActivity() {
        val intent = Intent(getContext(), SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

}
