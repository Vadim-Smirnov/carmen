package net.ginteam.carmen.kotlin.api.service

import net.ginteam.carmen.kotlin.api.ApiSettings
import net.ginteam.carmen.kotlin.manager.ApiManager
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import retrofit2.http.*
import rx.Observable

/**
 * Created by vadik on 09.03.17.
 */
interface NewsService {

    @GET(ApiSettings.News.ARTICLES)
    fun fetchNews(
            @Query(ApiSettings.News.Params.PAGE) page: Int
    ): Observable<ResponseModel<MutableList <NewsModel>>>

    @GET(ApiSettings.News.GET_NEWS_BY_ID)
    fun fetchNewsDetails(
            @Path(ApiSettings.News.Params.ID) newsId: Int
    ): Observable <ResponseModel <NewsModel>>

    @GET(ApiSettings.News.GET_POPULAR_NEWS)
    fun fetchPopularNews(
            @Query(ApiSettings.News.Params.DAYS) forDays: Int,
            @Query(ApiSettings.News.Params.PAGE) page: Int
    ): Observable<ResponseModel<MutableList <NewsModel>>>

    @GET(ApiSettings.Auth.GET_USER_FAVORITES_NEWS)
    fun fetchUserFavoritesNews(): Observable <ResponseModel <MutableList <NewsModel>>>

    @POST(ApiSettings.Auth.USER_FAVORITES_NEWS)
    fun addUserFavoritesNews(
            @Path(ApiSettings.Auth.Params.ID) newsId: Int
    ): Observable <ResponseModel <String>>

    @DELETE(ApiSettings.Auth.USER_FAVORITES_NEWS)
    fun removeUserFavoritesNews(
            @Path(ApiSettings.Auth.Params.ID) newsId: Int
    ): Observable <ResponseModel <String>>

    companion object {
        fun create(): NewsService = ApiManager.retrofit.create(NewsService::class.java)
    }

}