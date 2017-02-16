package net.ginteam.carmen.kotlin

import android.content.res.Resources
import android.support.annotation.DrawableRes
import android.support.design.internal.NavigationMenuView
import android.support.design.widget.NavigationView
import com.google.gson.Gson
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.FilterModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

fun <T> Observable <ResponseModel <T>>.asyncWithCache(cache: Boolean = true): Observable<ResponseModel<T>> {
    val originObservable = this
    val newObservable = originObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    if (cache) {
        newObservable.cache()
    }
    return newObservable
}

fun Resources.getFilters(): Array <FilterModel> {
    val json = CarmenApplication.getContext().getString(R.string.filters_example)
    val filterModels = Gson().fromJson(json, Array<FilterModel>::class.java)
    return filterModels
}

fun NavigationView.disableScrollbars() {
    val navigationMenuView = getChildAt(0)
    navigationMenuView.isVerticalScrollBarEnabled = false
}

@DrawableRes
fun CategoryModel.imageResourceId(): Int {
    when(id) {
        1 -> return R.drawable.ic_sto
        10 -> return R.drawable.ic_carwash
        18 -> return R.drawable.ic_tire
        32 -> return R.drawable.ic_refuelling
        else -> return R.drawable.ic_shop
    }
}