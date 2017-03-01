package net.ginteam.carmen.kotlin.api.service

import net.ginteam.carmen.kotlin.api.ApiSettings
import net.ginteam.carmen.kotlin.manager.ApiManager
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import retrofit2.http.*
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */
interface CompanyService {

    @GET(ApiSettings.Catalog.GET_COMPANIES)
    fun fetchCompanies(
            @Path(ApiSettings.Catalog.Params.ID) categoryId: Int,
            @Query(ApiSettings.Catalog.Params.PAGE) paginationPage: Int,
            @Query(ApiSettings.Catalog.Params.ORDER_BY) sortField: String,
            @Query(ApiSettings.Catalog.Params.SORTED_BY) sortType: String,
            @Query(ApiSettings.Catalog.Params.SEARCH) filter: String,
            @Query(ApiSettings.Catalog.Params.SEARCH_FIELD_TYPE) searchField: String = "name:ilike;city_id:=;rating:=;price_rel:="
    ): Observable <ResponseModel <MutableList <CompanyModel>>>

    @GET(ApiSettings.Catalog.GET_COMPANIES)
    fun fetchCompaniesForMap(
            @Path(ApiSettings.Catalog.Params.ID) categoryId: Int,
            @Query(ApiSettings.Catalog.Params.BOUNDS) bounds: String,
            @Query(ApiSettings.Catalog.Params.SEARCH) filter: String,
            @Query(ApiSettings.Catalog.Params.SEARCH_FIELD_TYPE) searchField: String = "name:ilike;city_id:=;rating:=;price_rel:="
    ): Observable <ResponseModel <MutableList <CompanyModel>>>

    @GET(ApiSettings.Catalog.GET_COMPANY_BY_ID)
    fun fetchCompany(
            @Path(ApiSettings.Catalog.Params.ID) companyId: Int,
            @Query(ApiSettings.Catalog.Relations.WITH) relations: String
    ): Observable <ResponseModel <CompanyModel>>

    @GET(ApiSettings.Catalog.GET_POPULAR_COMPANIES_BY_CITY_ID)
    fun fetchPopularCompanies(
            @Path(ApiSettings.Catalog.Params.CITY_ID) cityId: Int
    ): Observable <ResponseModel <List <CompanyModel>>>

    /*
        User companies
     */

    @GET(ApiSettings.Auth.GET_USER_FAVORITE_COMPANIES)
    fun fetchUserFavoriteCompanies(): Observable <ResponseModel <MutableList <CompanyModel>>>

    @POST(ApiSettings.Auth.GET_FAVORITE_COMPANY_BY_ID)
    fun addUserFavoriteCompany(
            @Path(ApiSettings.Auth.Params.ID) companyId: Int
    ): Observable <ResponseModel <String>>

    @DELETE(ApiSettings.Auth.GET_FAVORITE_COMPANY_BY_ID)
    fun removeUserFavoriteCompany(
            @Path(ApiSettings.Auth.Params.ID) companyId: Int
    ): Observable <ResponseModel <String>>

    @GET(ApiSettings.Auth.GET_USER_RECENTLY_WATCHED)
    fun fetchUserRecentlyWatched(): Observable <ResponseModel <List <CompanyModel>>>

    companion object {
        fun create(): CompanyService = ApiManager.retrofit.create(CompanyService::class.java)
    }

}