package net.ginteam.carmen.kotlin.view.activity.authentication

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.widget.EditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.Order
import com.mobsandgeeks.saripaar.annotation.Password
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseSignContract
import net.ginteam.carmen.kotlin.view.activity.BaseActivity

abstract class BaseSignActivity <in V : BaseSignContract.View, T : BaseSignContract.Presenter <V>>
    : BaseActivity<V, T>(), BaseSignContract.View, Validator.ValidationListener {

    @Order(2)
    @Email(messageResId = R.string.wrong_email_string)
    protected lateinit var mEditTextEmail: EditText

    @Order(3)
    @Password(messageResId = R.string.wrong_password_string)
    protected lateinit var mEditTextPassword: EditText

    protected lateinit var mFieldsValidator: Validator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

        updateViewDependencies()
        updateDependencies()
    }

    override fun showMainActivity() {
        showMessage("SHOw MAIN")
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        val firstError = errors?.get(0)
        showError(firstError?.getCollatedErrorMessage(getContext()))
        firstError?.view?.requestFocus()
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    override abstract fun onValidationSucceeded()

    open protected fun updateViewDependencies() {
        mEditTextEmail = findViewById(R.id.edit_text_email) as EditText
        mEditTextPassword = findViewById(R.id.edit_text_password) as EditText

        findViewById(R.id.text_view_skip_auth).setOnClickListener {
            finish()
        }
    }

    open protected fun updateDependencies() {
        mFieldsValidator = Validator(this)
        mFieldsValidator.setValidationListener(this)
    }

}
