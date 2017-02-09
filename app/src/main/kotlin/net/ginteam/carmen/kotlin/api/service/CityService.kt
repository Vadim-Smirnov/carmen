package net.ginteam.carmen.kotlin.api.service

import net.ginteam.carmen.kotlin.api.ApiSettings
import net.ginteam.carmen.kotlin.model.CityModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import retrofit2.http.GET
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */
interface CityService {

    @GET(ApiSettings.Catalog.GET_CITIES)
    fun fetchCities(): Observable <ResponseModel <List <CityModel>>>

    @GET(ApiSettings.Catalog.GET_CITY_BY_POINT)
    fun fetchCity(): Observable <ResponseModel <CityModel>>

}