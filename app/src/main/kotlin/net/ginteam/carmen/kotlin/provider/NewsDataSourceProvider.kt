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

    fun fetchNews(page: Int): Observable<ResponseModel<MutableList <NewsModel>>>
    fun fetchPopularNews(forDays: Int, page: Int): Observable<ResponseModel<MutableList <NewsModel>>>
    fun fetchNewsDetails(newsId: Int): Observable <ResponseModel <NewsModel>>
    fun fetchUserFavoritesNews(): Observable <ResponseModel <MutableList <NewsModel>>>
    fun addUserFavoritesNews(newsId: Int): Observable <ResponseModel <String>>
    fun removeUserFavoritesNews(newsId: Int): Observable <ResponseModel <String>>
}

class OnlineNewsDataSourceProvider : NewsDataSourceProvider {

    private val newsService: NewsService = NewsService.create()

    override fun fetchNews(page: Int)
            : Observable<ResponseModel<MutableList<NewsModel>>>
            = newsService.fetchNews(page).asyncWithCache()

    override fun fetchNewsDetails(newsId: Int)
            : Observable <ResponseModel <NewsModel>>
            = newsService.fetchNewsDetails(newsId).asyncWithCache()

    override fun fetchPopularNews(forDays: Int, page: Int)
            : Observable<ResponseModel<MutableList<NewsModel>>>
            = newsService.fetchPopularNews(forDays, page).asyncWithCache()

    override fun fetchUserFavoritesNews()
            : Observable<ResponseModel<MutableList<NewsModel>>>
            = newsService.fetchUserFavoritesNews().asyncWithCache()

    override fun addUserFavoritesNews(newsId: Int)
            : Observable<ResponseModel<String>>
            = newsService.addUserFavoritesNews(newsId).asyncWithCache()

    override fun removeUserFavoritesNews(newsId: Int)
            : Observable<ResponseModel<String>>
            = newsService.removeUserFavoritesNews(newsId).asyncWithCache()

}