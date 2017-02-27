package net.ginteam.carmen.kotlin.api.response

import net.ginteam.carmen.kotlin.model.ErrorModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

abstract class ModelSubscriber<T> : Subscriber <ResponseModel <T>>() {

    override fun onCompleted() {

    }

    override fun onError(e: Throwable) {
        var isNetwork: Boolean = false
        var message: String = ""

        if (isNetworkException(e)) {
            isNetwork = true
        } else if (e is HttpException) {
            message = parseHttpException(e)?.message ?: ""
        }

        error(message, isNetwork)
    }

    override fun onNext(t: ResponseModel<T>) {
        if (t.meta != null) {
            success(t.data, t.meta.pagination)
            return
        }
        success(t.data)
    }

    abstract fun success(model: T)
    open fun success(model: T, pagination: PaginationModel) {}
    abstract fun error(message: String, isNetworkError: Boolean)

    private fun isNetworkException(throwable: Throwable): Boolean {
        return throwable is SocketException ||
                throwable is UnknownHostException ||
                throwable is SocketTimeoutException
    }

    private fun parseHttpException(e: HttpException): ErrorModel? {
        return if (e.response()?.errorBody() != null) {
            ErrorModel.parseError(e.response().errorBody().string())
        } else {
            null
        }
    }
}

abstract class MetaSubscriber<T> : ModelSubscriber <T>() {

    abstract override fun success(model: T, pagination: PaginationModel)
    override fun success(model: T) {}

}