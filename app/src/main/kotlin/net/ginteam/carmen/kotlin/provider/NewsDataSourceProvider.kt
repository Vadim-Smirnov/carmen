package net.ginteam.carmen.kotlin.provider

import net.ginteam.carmen.kotlin.api.service.NewsService
import net.ginteam.carmen.kotlin.asyncWithCache
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import rx.Observable

/**
 * Created by vadik on 09.03.17.
 */

interface NewsDataSourceProvider {

    fun fetchCompaniesCountWithParameters(page: Int): Observable<ResponseModel<MutableList <NewsModel>>>

}

class OnlineNewsDataSourceProvider : NewsDataSourceProvider {

    private val newsService: NewsService = NewsService.create()

    override fun fetchCompaniesCountWithParameters(page: Int)
            : Observable<ResponseModel<MutableList<NewsModel>>>
            = newsService.fetchCompaniesCountWithParameters(page).asyncWithCache()
}