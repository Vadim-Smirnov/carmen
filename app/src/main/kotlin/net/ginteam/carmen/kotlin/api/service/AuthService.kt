package net.ginteam.carmen.kotlin.api.service

import net.ginteam.carmen.kotlin.api.ApiSettings
import net.ginteam.carmen.kotlin.manager.ApiManager
import net.ginteam.carmen.kotlin.model.AuthModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import net.ginteam.carmen.kotlin.model.UserModel
import retrofit2.http.*
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

interface AuthService {

    @POST(ApiSettings.Auth.USER_LOGIN)
    fun userLogin(
            @Query(ApiSettings.Auth.Params.EMAIL) email: String,
            @Query(ApiSettings.Auth.Params.PASSWORD) password: String,
            @Query(ApiSettings.Auth.Params.DEVICE_ID) deviceId: String
    ): Observable <ResponseModel <AuthModel>>

    @POST(ApiSettings.Auth.USER_REGISTER)
    fun userRegister(
            @Query(ApiSettings.Auth.Params.NAME) name: String,
            @Query(ApiSettings.Auth.Params.EMAIL) email: String,
            @Query(ApiSettings.Auth.Params.PASSWORD) password: String,
            @Query(ApiSettings.Auth.Params.DEVICE_ID) deviceId: String
    ): Observable <ResponseModel <AuthModel>>

    @GET(ApiSettings.Auth.GET_CURRENT_USER)
    fun getCurrentUser(): Observable <ResponseModel <UserModel>>

    /*
        Device
     */

    @POST(ApiSettings.Auth.DEVICE_INIT)
    fun deviceRegister(
            @Query(ApiSettings.Auth.Params.DEVICE_ID) deviceId: String,
            @Query(ApiSettings.Auth.Params.PUSH_TOKEN) pushToken: String,
            @Query(ApiSettings.Auth.Params.DEVICE_TYPE) deviceType: String
    ): Observable <ResponseModel <String>>

    @PUT(ApiSettings.Auth.UPDATE_NOTIFICATION_STATUS)
    fun updateNotificationStatus(
            @Path(ApiSettings.Auth.Params.ID) notificationId: String
    ): Observable <ResponseModel <String>>

    companion object {
        fun create(): AuthService = ApiManager.retrofit.create(AuthService::class.java)
    }

}