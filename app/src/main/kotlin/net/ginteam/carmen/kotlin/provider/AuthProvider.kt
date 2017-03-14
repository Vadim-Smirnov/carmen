package net.ginteam.carmen.kotlin.provider

import net.ginteam.carmen.kotlin.api.service.AuthService
import net.ginteam.carmen.kotlin.asyncWithCache
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.AuthModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import net.ginteam.carmen.kotlin.model.UserModel
import net.ginteam.carmen.utils.DeviceUtils
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

interface AuthProvider {

    var currentCachedUser: UserModel?

    fun userLogin(email: String, password: String): Observable <ResponseModel <AuthModel>>
    fun userRegister(name: String, email: String, password: String): Observable <ResponseModel <AuthModel>>
    fun fetchCurrentUser(): Observable <ResponseModel <UserModel>>

    /*
        Device
     */

    fun deviceRegister(deviceId: String, pushToken: String, deviceType: String): Observable <ResponseModel <String>>
    fun updateNotificationStatus(notificationId: String): Observable <ResponseModel <String>>

}

object AuthenticationProvider : AuthProvider {
    private val authService: AuthService = AuthService.create()

    override var currentCachedUser: UserModel? = null

    override fun userLogin(email: String, password: String): Observable <ResponseModel <AuthModel>>
            = authService
            .userLogin(email, password, DeviceUtils.getDeviceId())
            .asyncWithCache(false)
            .doOnNext {
                cacheUser(it.data.user)
                saveUserAccessToken(it.data.token)
            }

    override fun userRegister(name: String, email: String, password: String): Observable <ResponseModel <AuthModel>>
            = authService
            .userRegister(name, email, password, DeviceUtils.getDeviceId())
            .asyncWithCache(false)
            .doOnNext {
                cacheUser(it.data.user)
                saveUserAccessToken(it.data.token)
            }

    override fun fetchCurrentUser(): Observable <ResponseModel <UserModel>>
            = authService.getCurrentUser().asyncWithCache().doOnNext { cacheUser(it.data) }

    override fun deviceRegister(deviceId: String, pushToken: String, deviceType: String): Observable<ResponseModel<String>>
            = authService.deviceRegister(deviceId, pushToken, deviceType).asyncWithCache()

    override fun updateNotificationStatus(notificationId: String): Observable <ResponseModel <String>>
            = authService.updateNotificationStatus(notificationId).asyncWithCache()

    private fun cacheUser(user: UserModel) {
        currentCachedUser = user
    }

    private fun saveUserAccessToken(token: String) {
        SharedPreferencesManager.userAccessToken = token
    }
}