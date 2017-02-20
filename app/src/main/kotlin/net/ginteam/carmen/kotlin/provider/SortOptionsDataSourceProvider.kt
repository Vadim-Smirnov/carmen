package net.ginteam.carmen.kotlin.provider

import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.kotlin.getSortOptions
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.SortModel
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/20/17.
 */

interface SortOptionsDataSourceProvider {

    fun fetchSortOptions(category: CategoryModel): Observable <List <SortModel>>

}

class OfflineSortOptionsDataSourceProvider : SortOptionsDataSourceProvider {

    override fun fetchSortOptions(category: CategoryModel): Observable<List<SortModel>> {
        val resources = CarmenApplication.getContext().resources
        val sortOptions: List <SortModel> = resources.getSortOptions()
        return Observable.just(sortOptions)
    }
}