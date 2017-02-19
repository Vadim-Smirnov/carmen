package net.ginteam.carmen.kotlin.provider

import net.ginteam.carmen.kotlin.api.service.CategoryService
import net.ginteam.carmen.kotlin.asyncWithCache
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

interface CategoryDataSourceProvider {

    fun fetchCategories(cityId: Int): Observable <ResponseModel <List <CategoryModel>>>

}

class OnlineCategoryDataSourceProvider : CategoryDataSourceProvider {
    private val categoryService: CategoryService = CategoryService.create()

    override fun fetchCategories(cityId: Int): Observable<ResponseModel<List<CategoryModel>>>
            = categoryService.fetchCategories(cityId).asyncWithCache()
}