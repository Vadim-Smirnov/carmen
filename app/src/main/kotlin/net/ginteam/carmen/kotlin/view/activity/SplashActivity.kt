package net.ginteam.carmen.kotlin.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import net.ginteam.carmen.kotlin.contract.AuthContract
import net.ginteam.carmen.kotlin.presenter.authentication.AuthenticationPresenter
import net.ginteam.carmen.kotlin.view.activity.authentication.SignInActivity

class SplashActivity : BaseLocationActivity<AuthContract.View, AuthContract.Presenter>(),
        AuthContract.View {

    override var mPresenter: AuthContract.Presenter = AuthenticationPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.fetchUserLocation()
    }

    override fun getLayoutResId(): Int = 0

    override fun showCitiesDialog() {
        Toast.makeText(getContext(), "CITIES SHOW", Toast.LENGTH_SHORT).show()
        showMainActivity()
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
