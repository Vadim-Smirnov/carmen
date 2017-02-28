package net.ginteam.carmen.kotlin.presenter.company.map

import android.util.Log
import com.google.android.gms.maps.model.LatLngBounds
import net.ginteam.carmen.kotlin.api.response.MetaSubscriber
import net.ginteam.carmen.kotlin.contract.MapCompaniesFragmentContract
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.presenter.company.list.BaseCompaniesPresenter

/**
 * Created by eugene_shcherbinock on 2/21/17.
 */

class MapCompaniesFragmentPresenter : BaseCompaniesPresenter<MapCompaniesFragmentContract.View>(), MapCompaniesFragmentContract.Presenter {

    override fun fetchCompanies(category: CategoryModel, filterQuery: String, mapBounds: LatLngBounds) {
        mView?.showSearchView(false)
        mView?.showCompaniesView(false)

        val searchBounds: String = String
                .format(
                        "%f %f %f %f",
                        mapBounds.southwest.longitude, mapBounds.southwest.latitude,
                        mapBounds.northeast.longitude, mapBounds.northeast.latitude
                ).replace(",", ".").replace(" ", ",")

        mCompaniesProvider
                .fetchMapCompanies(category.id, searchBounds, filterQuery)
                .subscribe(object : MetaSubscriber <MutableList <CompanyModel>>() {
                    override fun success(model: MutableList<CompanyModel>, pagination: PaginationModel) {
                        Log.d("MapPresenter", "Receive companies: " + model.size)
                        if (!model.isEmpty()) {
                            mView?.showCompaniesView(true)
                            mView?.showCompanies(model)
                            return
                        }
                        mView?.showCompaniesView(false)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }
}