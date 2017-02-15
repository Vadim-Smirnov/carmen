package net.ginteam.carmen.kotlin.contract

import android.content.Context
import android.support.annotation.StringRes

/**
 * Created by eugene_shcherbinock on 2/13/17.
 */

object BaseContract {

    interface View {
        fun getContext(): Context

        fun showError(message: String?)
        fun showError(@StringRes messageResId: Int)

        fun showMessage(message: String)
        fun showMessage(@StringRes messageResId: Int)

        fun showLoading(show: Boolean, messageResId: Int = 0)
    }

    interface Presenter<in V : View> {
        fun attachView(view: V)
        fun detachView()
    }

}