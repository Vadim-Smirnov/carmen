package net.ginteam.carmen.kotlin.presenter.authentication

import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.SignUpContract
import net.ginteam.carmen.kotlin.model.AuthModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.AuthProvider
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider

/**
 * Created by eugene_shcherbinock on 2/15/17.
 */
class SignUpPresenter : BasePresenter <SignUpContract.View>(), SignUpContract.Presenter {

    private val mAuthProvider: AuthProvider = AuthenticationProvider

    override fun signUp(name: String, email: String, password: String) {
        mView?.showLoading(true, R.string.registration_progress_message)

        mAuthProvider
                .userRegister(name, email, password)
                .subscribe(object : ModelSubscriber<AuthModel>() {
                    override fun success(model: AuthModel) {
                        mView?.showLoading(false)
                        mView?.showMainActivity()
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message)
                    }
                })
    }
}