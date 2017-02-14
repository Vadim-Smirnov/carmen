package net.ginteam.carmen.kotlin.api.service

import net.ginteam.carmen.kotlin.api.ApiSettings
import net.ginteam.carmen.kotlin.manager.ApiManager
import net.ginteam.carmen.kotlin.model.CategoryModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import retrofit2.http.GET
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */
interface CategoryService {

    @GET(ApiSettings.Catalog.GET_CATEGORIES)
    fun fetchCategories(): Observable <ResponseModel <List <CategoryModel>>>

    companion object {
        fun create(): CategoryService = ApiManager.retrofit.create(CategoryService::class.java)
    }

}