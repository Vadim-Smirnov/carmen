package net.ginteam.carmen.kotlin.provider

import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.kotlin.api.service.FilterService
import net.ginteam.carmen.kotlin.async
import net.ginteam.carmen.kotlin.getFilters
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.FilterModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/13/17.
 */

interface FiltersDataSourceProvider {

    fun fetchFiltersForCategory(categoryId: Int): Observable <Array<FilterModel>>
    fun fetchCompaniesCountWithParameters(categoryId: Int, filter: String = "", limit: Int = 1)
            : Observable <ResponseModel <List <CompanyModel>>>

}

class OnlineFiltersDataSourceProvider : FiltersDataSourceProvider {
    private val filtersService: FilterService = FilterService.create()

    override fun fetchFiltersForCategory(categoryId: Int): Observable <Array<FilterModel>> {
        val resources = CarmenApplication.getContext().resources
        val filters: Array <FilterModel> = resources.getFilters()
        return Observable.just(filters)
    }

    override fun fetchCompaniesCountWithParameters(categoryId: Int, filter: String, limit: Int)
            : Observable<ResponseModel<List<CompanyModel>>>
            = filtersService.fetchCompaniesCountWithParameters(categoryId, filter, limit).async()
}