package net.ginteam.carmen.kotlin.api.service

import net.ginteam.carmen.kotlin.api.ApiSettings
import net.ginteam.carmen.kotlin.model.AuthModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import net.ginteam.carmen.kotlin.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */
interface AuthService {

    @GET(ApiSettings.Auth.USER_LOGIN)
    fun userLogin(
            @Query(ApiSettings.Auth.Params.EMAIL) email: String,
            @Query(ApiSettings.Auth.Params.PASSWORD) password: String
    ): Observable <ResponseModel <AuthModel>>

    @GET(ApiSettings.Auth.USER_REGISTER)
    fun userRegister(
            @Query(ApiSettings.Auth.Params.NAME) name: String,
            @Query(ApiSettings.Auth.Params.EMAIL) email: String,
            @Query(ApiSettings.Auth.Params.PASSWORD) password: String
    ) : Observable <ResponseModel <AuthModel>>

    @GET(ApiSettings.Auth.GET_CURRENT_USER)
    fun getCurrentUser() : Observable <ResponseModel <UserModel>>

}