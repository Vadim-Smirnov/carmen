package net.ginteam.carmen.kotlin.view.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseContract

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */
abstract class BaseFragment <in V : BaseContract.View, T : BaseContract.Presenter <V>> : DialogFragment(),
        BaseContract.View {

    protected abstract var mPresenter: T

    protected lateinit var mFragmentView: View
    protected var mProgressDialog: SweetAlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mFragmentView = inflater!!.inflate(getLayoutResId(), container, false)

        updateViewDependencies()
        updateDependencies()

        mPresenter.attachView(this as V)

        return mFragmentView
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            setupDialogSettings()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    override fun showError(message: String?) {
        if (mProgressDialog != null && mProgressDialog!!.alerType == SweetAlertDialog.PROGRESS_TYPE) {
            mProgressDialog!!.changeAlertType(SweetAlertDialog.ERROR_TYPE)
            mProgressDialog!!.titleText = message
            return
        }
        mProgressDialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
        mProgressDialog!!.titleText = ""
        mProgressDialog!!.contentText = message
        mProgressDialog!!.show()
    }

    override fun showError(messageResId: Int) {
        showError(getString(messageResId))
    }

    override fun showMessage(message: String)
            = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    override fun showMessage(messageResId: Int)
            = Toast.makeText(context, messageResId, Toast.LENGTH_LONG).show()

    override fun showLoading(show: Boolean, messageResId: Int) {
        if (show) {
            mProgressDialog = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
            if (messageResId != 0) {
                mProgressDialog!!.titleText = getString(messageResId)
            }
            mProgressDialog!!.show()
            return
        }
        mProgressDialog?.dismiss()
    }

    open protected fun updateViewDependencies() {}
    open protected fun updateDependencies() {}

    private fun setupDialogSettings() {
        val dialogWidth = resources.getDimension(R.dimen.dialog_fragment_width).toInt()
        val dialogHeight = resources.getDimension(R.dimen.dialog_fragment_height).toInt()
        dialog.window!!.setLayout(dialogWidth, dialogHeight)
        dialog.setCancelable(true)
    }

}