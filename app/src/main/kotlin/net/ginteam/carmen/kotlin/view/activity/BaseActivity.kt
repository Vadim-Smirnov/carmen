package net.ginteam.carmen.kotlin.view.activity

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseContract
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity<in V : BaseContract.View, T : BaseContract.Presenter <V>>
    : AppCompatActivity(), BaseContract.View {

    protected abstract var mPresenter: T
    protected var mToolbar: Toolbar? = null

    protected var mProgressDialog: SweetAlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId())

            updateDependencies()
            updateViewDependencies()
        }

        mPresenter.attachView(this as V)
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    open protected fun getNetworkErrorAction(): (() -> Unit)? = null

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun attachBaseContext(newBase: Context?)
            = super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))

    override fun getContext(): Context = this

    override fun showError(message: String?, isNetworkError: Boolean, confirmAction: (() -> Unit)?) {
        if (mProgressDialog != null && mProgressDialog!!.alerType == SweetAlertDialog.PROGRESS_TYPE) {
            mProgressDialog!!.changeAlertType(SweetAlertDialog.ERROR_TYPE)
            mProgressDialog!!.titleText = getString(R.string.error_dialog_title)
            mProgressDialog!!.contentText = message
            mProgressDialog!!.setCancelable(false)

            if (isNetworkError) {
                prepareNetworkErrorDialog(getNetworkErrorAction())
            }
            return
        }
        mProgressDialog = SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
        mProgressDialog!!.titleText = getString(R.string.error_dialog_title)
        mProgressDialog!!.contentText = message
        mProgressDialog!!.setCancelable(false)

        if (getString(R.string.access_denied_message) == message) {
            prepareAuthorizationErrorDialog(confirmAction)
        }

        mProgressDialog!!.show()
    }

    override fun showError(messageResId: Int, isNetworkError: Boolean, confirmAction: (() -> Unit)?) {
        showError(getString(messageResId), isNetworkError, confirmAction)
    }

    override fun showMessage(message: String) {
        mProgressDialog = SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
        mProgressDialog!!.titleText = getString(R.string.warning_dialog_title)
        mProgressDialog!!.contentText = message
        mProgressDialog!!.show()
    }

    override fun showMessage(messageResId: Int) {
        showMessage(getString(messageResId))
    }

    override fun showLoading(show: Boolean, messageResId: Int) {
        if (show) {
            mProgressDialog = SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
            if (messageResId != 0) {
                mProgressDialog!!.titleText = getString(messageResId)
            }
            mProgressDialog!!.show()
            return
        }
        mProgressDialog?.dismiss()
    }

    protected fun setToolbarTitle(title: String) {
        (mToolbar?.findViewById(R.id.text_view_toolbar_title) as TextView).text = title
        setToolbarSubtitle("")
    }

    protected fun getToolbarTitle(): String
            = (mToolbar?.findViewById(R.id.text_view_toolbar_title) as TextView).text.toString()

    protected fun setToolbarSubtitle(subtitle: String) {
        (mToolbar?.findViewById(R.id.text_view_toolbar_subtitle) as TextView).text = subtitle
    }

    protected fun getToolbarSubtitle(): String
            = (mToolbar?.findViewById(R.id.text_view_toolbar_subtitle) as TextView).text.toString()

    open protected fun updateViewDependencies() {
        mToolbar = findViewById(R.id.toolbar) as Toolbar?
        mToolbar?.let {
            setSupportActionBar(mToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    open protected fun updateDependencies() {}

    private fun prepareAuthorizationErrorDialog(withConfirmAction: (() -> Unit)?) {
        mProgressDialog!!.cancelText = getString(R.string.sign_in_later_string)
        mProgressDialog!!.confirmText = getString(R.string.sign_in_now_string)

        withConfirmAction?.let {
            mProgressDialog!!.setConfirmClickListener {
                withConfirmAction.invoke()
                it.dismissWithAnimation()
            }
        }
    }

    private fun prepareNetworkErrorDialog(withNetworkErrorAction: (() -> Unit)?) {
        mProgressDialog!!.contentText = getString(R.string.no_network_connection_message)
        mProgressDialog!!.confirmText = getString(R.string.try_again_string)

        withNetworkErrorAction?.let {
            mProgressDialog!!.setConfirmClickListener {
                withNetworkErrorAction.invoke()
                it.dismissWithAnimation()
            }
        }
    }

}
