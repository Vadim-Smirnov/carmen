package net.ginteam.carmen.kotlin.provider

import net.ginteam.carmen.kotlin.api.service.CityService
import net.ginteam.carmen.kotlin.asyncWithCache
import net.ginteam.carmen.kotlin.model.CityModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

interface CitiesDataSourceProvider {

    fun fetchCities(): Observable <ResponseModel <List <CityModel>>>
    fun fetchUserCity(): Observable <ResponseModel <CityModel>>

}

class OnlineCitiesDataSourceProvider: CitiesDataSourceProvider {
    private val citiesService: CityService = CityService.create()

    override fun fetchCities(): Observable<ResponseModel<List<CityModel>>>
            = citiesService.fetchCities().asyncWithCache()

    override fun fetchUserCity(): Observable<ResponseModel<CityModel>>
            = citiesService.fetchCity().asyncWithCache()
}