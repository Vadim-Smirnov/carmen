package net.ginteam.carmen.kotlin.api.service

import net.ginteam.carmen.kotlin.api.ApiSettings
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */
interface FilterService {

    @GET(ApiSettings.Catalog.GET_COMPANIES)
    fun fetchCompaniesCountWithParameters(
            @Path(ApiSettings.Catalog.Params.ID) categoryId: Int,
            @Query(ApiSettings.Catalog.Params.SEARCH) filter: String,
            @Query(ApiSettings.Catalog.Params.LIMIT) limit: Int
    ): Observable <ResponseModel <List <CompanyModel>>>

}