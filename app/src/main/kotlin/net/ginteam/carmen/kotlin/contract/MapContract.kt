package net.ginteam.carmen.kotlin.contract

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import net.ginteam.carmen.kotlin.model.CategoryModel

/**
 * Created by eugene_shcherbinock on 2/21/17.
 */

object MapActivityContract {

    interface View : LocationContract.View {

        fun showCompaniesMapFragment(startLocation: LatLng)

    }

    interface Presenter : LocationContract.Presenter <View>

}

object MapCompaniesFragmentContract {

    interface View : BaseCompaniesContract.View {

        fun showSearchView(show: Boolean)
        fun showCompaniesView(show: Boolean)

    }

    interface Presenter : BaseCompaniesContract.Presenter <View> {

        fun fetchCompanies(category: CategoryModel, filterQuery: String, mapBounds: LatLngBounds)

    }

}

object MapCompanyFragmentContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter <View>

}