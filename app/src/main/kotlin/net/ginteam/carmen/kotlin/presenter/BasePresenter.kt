package net.ginteam.carmen.kotlin.presenter

import net.ginteam.carmen.kotlin.contract.BaseContract

/**
 * Created by eugene_shcherbinock on 2/13/17.
 */

open class BasePresenter<V : BaseContract.View> : BaseContract.Presenter <V> {

    protected var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        if (mView != null) {
            mView = null
        }
    }
}