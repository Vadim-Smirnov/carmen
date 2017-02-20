package net.ginteam.carmen.kotlin.view.activity.authentication

import android.widget.EditText
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword
import com.mobsandgeeks.saripaar.annotation.Order
import com.mobsandgeeks.saripaar.annotation.Pattern
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.SignUpContract
import net.ginteam.carmen.kotlin.presenter.authentication.SignUpPresenter

class SignUpActivity : BaseSignActivity <SignUpContract.View, SignUpContract.Presenter>(), SignUpContract.View {

    override var mPresenter: SignUpContract.Presenter = SignUpPresenter()

    @Order(1)
    @Pattern(regex = "^(([\\p{L}]+){1}( ?[\\p{L}]+)?){1}$", messageResId = R.string.wrong_name_string)
    private lateinit var mEditTextName: EditText

    @Order(4)
    @ConfirmPassword(messageResId = R.string.wrong_password_confirmation_string)
    private lateinit var mEditTextConfirmPassword: EditText

    override fun getLayoutResId(): Int = R.layout.activity_sign_up

    override fun getNetworkErrorAction(): (() -> Unit)? = {
        onValidationSucceeded()
    }

    override fun onValidationSucceeded() {
        mPresenter.signUp(
                mEditTextName.text.toString(),
                mEditTextEmail.text.toString(),
                mEditTextPassword.text.toString())
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mEditTextName = findViewById(R.id.edit_text_name) as EditText
        mEditTextConfirmPassword = findViewById(R.id.edit_text_confirm_password) as EditText

        findViewById(R.id.button_sign_up).setOnClickListener { mFieldsValidator.validate() }
        findViewById(R.id.text_view_sign_in).setOnClickListener { onBackPressed() }
    }

}
