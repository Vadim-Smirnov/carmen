package net.ginteam.carmen.kotlin.view.activity.authentication

import android.app.Activity
import android.content.Intent
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.SignInContract
import net.ginteam.carmen.kotlin.presenter.authentication.SignInPresenter

class SignInActivity : BaseSignActivity <SignInContract.View, SignInContract.Presenter>(), SignInContract.View {

    override var mPresenter: SignInContract.Presenter = SignInPresenter()

    companion object {
        const val SIGN_IN_REQUEST_CODE = 100
    }

    override fun getLayoutResId(): Int = R.layout.activity_sign_in

    override fun getNetworkErrorAction(): (() -> Unit)? = {
        onValidationSucceeded()
    }

    override fun onValidationSucceeded() {
        mPresenter.signIn(mEditTextEmail.text.toString(), mEditTextPassword.text.toString())
    }

    //TODO: rename
    override fun showMainActivity() {
        // if activity was started for result from main
        callingActivity?.let {
            setResult(if (mPresenter.isUserSignedInSuccessfully()) {
                Activity.RESULT_OK
            } else {
                Activity.RESULT_CANCELED
            })
            finish()
            return
        }
        super.showMainActivity()
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        findViewById(R.id.button_sign_in).setOnClickListener {
            mFieldsValidator.validate()
        }
        findViewById(R.id.button_sign_in_facebook).setOnClickListener {
            mPresenter.facebookSignIn()
        }
        findViewById(R.id.button_sign_in_google).setOnClickListener {
            mPresenter.googleSignIn()
        }
        findViewById(R.id.text_view_sign_up).setOnClickListener {
            val intent = Intent(getContext(), SignUpActivity::class.java)
            startActivity(intent)
        }
    }

}
