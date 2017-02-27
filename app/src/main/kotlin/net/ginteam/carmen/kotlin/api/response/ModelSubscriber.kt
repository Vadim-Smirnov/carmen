package net.ginteam.carmen.kotlin.api.response

import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import rx.Subscriber
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

interface ModelCallback<T> {

    fun success(model: T)
    fun success(model: T, pagination: PaginationModel) {}
    fun error(message: String, isNetworkError: Boolean = false)

}

abstract class ModelSubscriber<T> : Subscriber <ResponseModel <T>>(), ModelCallback <T> {

    override fun onCompleted() {

    }

    override fun onError(e: Throwable) {
        error(e.message ?: "", isNetworkException(e))
    }

    override fun onNext(t: ResponseModel<T>) {
        if (t.success) {
            if (t.meta != null) {
                success(t.data, t.meta.pagination)
                return
            }
            success(t.data)
            return
        }
        error(t.message)
    }

    abstract override fun success(model: T)
    abstract override fun error(message: String, isNetworkError: Boolean)

    private fun isNetworkException(throwable: Throwable): Boolean {
        return throwable is SocketException ||
                throwable is UnknownHostException ||
                throwable is SocketTimeoutException
    }

}

abstract class MetaSubscriber<T> : ModelSubscriber <T>(), ModelCallback <T> {

    abstract override fun success(model: T, pagination: PaginationModel)
    override fun success(model: T) {}

}