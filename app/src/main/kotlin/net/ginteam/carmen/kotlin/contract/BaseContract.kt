package net.ginteam.carmen.kotlin.contract

import android.content.Context
import android.support.annotation.StringRes
import net.ginteam.carmen.R

/**
 * Created by eugene_shcherbinock on 2/13/17.
 */

object BaseContract {

    interface View {
        fun getContext(): Context

        fun showError(message: String?, isNetworkError: Boolean = false, confirmAction: (() -> Unit)? = null)
        fun showError(@StringRes messageResId: Int, isNetworkError: Boolean = false, confirmAction: (() -> Unit)? = null)

        fun showMessage(message: String)
        fun showMessage(@StringRes messageResId: Int)

        fun showLoading(show: Boolean, @StringRes messageResId: Int = R.string.loading_progress_message)
    }

    interface Presenter<in V : View> {
        fun attachView(view: V)
        fun detachView()
    }

}