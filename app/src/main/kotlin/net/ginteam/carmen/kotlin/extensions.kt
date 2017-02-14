package net.ginteam.carmen.kotlin

import android.content.res.Resources
import com.google.gson.Gson
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.FilterModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

fun <T> Observable <ResponseModel <T>>.async(): Observable<ResponseModel<T>>
        = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).cache()

fun Resources.getFilters(): Array <FilterModel> {
    val json = CarmenApplication.getContext().getString(R.string.filters_example)
    val filterModels = Gson().fromJson(json, Array<FilterModel>::class.java)
    return filterModels
}