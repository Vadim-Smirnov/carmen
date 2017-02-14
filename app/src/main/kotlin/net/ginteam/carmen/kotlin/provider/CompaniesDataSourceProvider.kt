package net.ginteam.carmen.kotlin.provider

import net.ginteam.carmen.kotlin.api.service.CompanyService
import net.ginteam.carmen.kotlin.async
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

interface CompaniesDataSourceProvider {

    fun fetchCompanies(categoryId: Int, bounds: String = "", filter: String = "",
                       sortField: String = "", sortType: String = "", paginationPage: Int = 1
    ): Observable <ResponseModel <List <CompanyModel>>>

    fun fetchCompany(companyId: Int, relations: String = ""): Observable <ResponseModel <CompanyModel>>
    fun fetchPopularCompanies(cityId: Int): Observable <ResponseModel <List <CompanyModel>>>

    /*
        User companies
     */

    fun fetchUserFavoriteCompanies(): Observable <ResponseModel <List <CompanyModel>>>
    fun addUserFavoriteCompany(companyId: Int): Observable <ResponseModel <String>>
    fun removeUserFavoriteCompany(companyId: Int): Observable <ResponseModel <String>>
    fun fetchUserRecentlyWatched(): Observable <ResponseModel <List <CompanyModel>>>

}

class OnlineCompaniesDataSourceProvider : CompaniesDataSourceProvider {
    private val companiesService: CompanyService = CompanyService.create()

    override fun fetchCompanies(categoryId: Int, bounds: String, filter: String, sortField: String,
                                sortType: String, paginationPage: Int): Observable<ResponseModel<List<CompanyModel>>>
            = companiesService.fetchCompanies(categoryId, bounds, filter, sortField, sortType, paginationPage).async()

    override fun fetchCompany(companyId: Int, relations: String): Observable<ResponseModel<CompanyModel>>
            = companiesService.fetchCompany(companyId, relations).async()

    override fun fetchPopularCompanies(cityId: Int): Observable<ResponseModel<List<CompanyModel>>>
            = companiesService.fetchPopularCompanies(cityId).async()

    override fun fetchUserFavoriteCompanies(): Observable<ResponseModel<List<CompanyModel>>>
            = companiesService.fetchUserFavoriteCompanies().async()

    override fun addUserFavoriteCompany(companyId: Int): Observable<ResponseModel<String>>
            = companiesService.addUserFavoriteCompany(companyId).async()

    override fun removeUserFavoriteCompany(companyId: Int): Observable <ResponseModel <String>>
            = companiesService.removeUserFavoriteCompany(companyId).async()

    override fun fetchUserRecentlyWatched(): Observable<ResponseModel<List<CompanyModel>>>
            = companiesService.fetchUserRecentlyWatched().async()
}