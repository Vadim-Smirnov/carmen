package net.ginteam.carmen.kotlin.presenter.authentication

import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.SignInContract
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.AuthModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.AuthProvider
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider

/**
 * Created by eugene_shcherbinock on 2/15/17.
 */
class SignInPresenter : BasePresenter <SignInContract.View>(), SignInContract.Presenter {

    private val mAuthProvider: AuthProvider = AuthenticationProvider

    override fun signIn(email: String, password: String) {
        mView?.showLoading(true, R.string.authorization_progress_message)

        mAuthProvider
                .userLogin(email, password)
                .subscribe(object : ModelSubscriber <AuthModel>() {
                    override fun success(model: AuthModel) {
                        saveUserAccessToken(model.token)

                        mView?.showLoading(false)
                        mView?.showMainActivity()
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }

    override fun isUserSignedInSuccessfully(): Boolean
            = !SharedPreferencesManager.userAccessToken.isEmpty()

    override fun facebookSignIn() {
        throw UnsupportedOperationException("not implemented")
    }

    override fun googleSignIn() {
        throw UnsupportedOperationException("not implemented")
    }

    private fun saveUserAccessToken(token: String) {
        SharedPreferencesManager.userAccessToken = token
    }

}