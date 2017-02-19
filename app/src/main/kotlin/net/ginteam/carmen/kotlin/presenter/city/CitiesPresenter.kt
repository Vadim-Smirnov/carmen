package net.ginteam.carmen.kotlin.presenter.city

import net.ginteam.carmen.kotlin.api.response.ModelSubscriber
import net.ginteam.carmen.kotlin.contract.CitiesContract
import net.ginteam.carmen.kotlin.manager.PreferencesManager
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.CityModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.CitiesDataSourceProvider
import net.ginteam.carmen.kotlin.provider.OnlineCitiesDataSourceProvider

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

class CitiesPresenter : BasePresenter <CitiesContract.View>(), CitiesContract.Presenter {

    private val mCitiesProvider: CitiesDataSourceProvider = OnlineCitiesDataSourceProvider()

    override fun fetchCities() {
        mView?.showLoading(true)

        mCitiesProvider
                .fetchCities()
                .subscribe(object : ModelSubscriber <List <CityModel>>() {
                    override fun success(model: List<CityModel>) {
                        mView?.showLoading(false)
                        mView?.showCities(model)
                    }

                    override fun error(message: String) {
                        mView?.showError(message)
                    }
                })
    }

    override fun saveSelectedCity(city: CityModel) {
        val preferences: PreferencesManager = SharedPreferencesManager
        preferences.userCityModel = city
    }
}