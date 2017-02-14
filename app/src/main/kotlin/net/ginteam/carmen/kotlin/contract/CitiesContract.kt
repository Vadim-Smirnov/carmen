package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.CityModel

/**
 * Created by eugene_shcherbinock on 2/13/17.
 */
object CitiesContract {

    interface View : BaseContract.View {

        fun showCities(cities: List <CityModel>)

    }

    interface Presenter : BaseContract.Presenter <View> {

        fun fetchCities()
        fun saveSelectedCity(city: CityModel)

    }

}