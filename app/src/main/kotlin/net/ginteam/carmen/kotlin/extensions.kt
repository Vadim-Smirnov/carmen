package net.ginteam.carmen.kotlin

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.design.widget.NavigationView
import android.support.v13.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.gson.Gson
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.FilterModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import net.ginteam.carmen.kotlin.model.SortModel
import net.ginteam.carmen.kotlin.view.fragment.company.map.MapCompaniesFragment
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

fun AppCompatActivity.prepareFragment(@LayoutRes containerLayoutResId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(containerLayoutResId, fragment).commit()
}

fun Fragment.prepareFragment(@LayoutRes containerLayoutResId: Int, fragment: Fragment) {
    childFragmentManager.beginTransaction().replace(containerLayoutResId, fragment).commit()
}

fun MapCompaniesFragment.checkPermission(): Boolean {
    return !(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
}

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

fun Resources.getFilters(): List <FilterModel> {
    val json = CarmenApplication.getContext().getString(R.string.filters_example)
    val filters = Gson().fromJson(json, Array<FilterModel>::class.java)
    return filters.toList()
}

fun Resources.getSortOptions(): List <SortModel> {
    val json = CarmenApplication.getContext().getString(R.string.sorting_example)
    val sortOptions = Gson().fromJson(json, Array<SortModel>::class.java)
    return sortOptions.toList()
}

fun NavigationView.disableScrollbars() {
    val navigationMenuView = getChildAt(0)
    navigationMenuView.isVerticalScrollBarEnabled = false
}

@DrawableRes
fun CategoryModel.imageResourceId(): Int {
    when (id) {
        1 -> return R.drawable.ic_sto
        10 -> return R.drawable.ic_carwash
        18 -> return R.drawable.ic_tire
        32 -> return R.drawable.ic_refuelling
        27 -> return R.drawable.ic_parking
        else -> return R.drawable.ic_shop
    }
}

fun GoogleMap.animateCameraToLocation(location: LatLng, zoomLevel: Float = 12f) {
    animateCamera(CameraUpdateFactory.newCameraPosition(
            CameraPosition.Builder()
                    .target(location)
                    .zoom(zoomLevel)
                    .build())
    )
}

fun GoogleMap.getBounds(): LatLngBounds = projection.visibleRegion.latLngBounds