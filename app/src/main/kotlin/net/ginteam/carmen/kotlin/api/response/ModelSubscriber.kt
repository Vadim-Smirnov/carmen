package net.ginteam.carmen.kotlin.api.response

import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import rx.Subscriber

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

interface ModelCallback <T> {

    fun success(model: T)
    fun success(model: T, pagination: PaginationModel) {}
    fun error(message: String)

}

abstract class ModelSubscriber <T> : Subscriber <ResponseModel <T>>(), ModelCallback <T> {

    override fun onCompleted() {

    }

    override fun onError(e: Throwable) {
        error(e.message.let {
            it!!
        })
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
    abstract override fun error(message: String)

}

abstract class MetaSubscriber <T> : ModelSubscriber <T>(), ModelCallback <T> {

    abstract override fun success(model: T, pagination: PaginationModel)
    override fun success(model: T) {}

}