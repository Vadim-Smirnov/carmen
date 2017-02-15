package net.ginteam.carmen.kotlin.view.activity.authentication

import android.content.Intent
import android.os.Bundle
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.SignInContract
import net.ginteam.carmen.kotlin.presenter.authentication.SignInPresenter

class SignInActivity : BaseSignActivity <SignInContract.View, SignInContract.Presenter>(), SignInContract.View {

    override var mPresenter: SignInContract.Presenter = SignInPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutResId(): Int = R.layout.activity_sign_in

    override fun onValidationSucceeded() {
        mPresenter.signIn(mEditTextEmail.text.toString(), mEditTextPassword.text.toString())
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
