package net.ginteam.carmen.kotlin.view.activity.authentication

import android.os.Bundle
import android.widget.Toast
import net.ginteam.carmen.kotlin.contract.AuthContract
import net.ginteam.carmen.kotlin.presenter.authentication.AuthenticationPresenter
import net.ginteam.carmen.kotlin.view.activity.BaseLocationActivity

class AuthenticationActivity : BaseLocationActivity <AuthContract.View, AuthContract.Presenter>(),
        AuthContract.View {

    override var mPresenter: AuthContract.Presenter = AuthenticationPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.fetchUserLocation()
    }

    override fun showCitiesDialog() {
        Toast.makeText(getContext(), "CITIES SHOW", Toast.LENGTH_SHORT).show()
    }

    override fun showMainActivity() {
        Toast.makeText(getContext(), "CITIES SHOW", Toast.LENGTH_SHORT).show()
    }

    override fun showSignInActivity() {
        Toast.makeText(getContext(), "CITIES SHOW", Toast.LENGTH_SHORT).show()
    }

}
