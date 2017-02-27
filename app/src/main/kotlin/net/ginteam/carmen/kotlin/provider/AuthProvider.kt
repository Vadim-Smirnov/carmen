package net.ginteam.carmen.kotlin.provider

import net.ginteam.carmen.kotlin.api.service.AuthService
import net.ginteam.carmen.kotlin.asyncWithCache
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.AuthModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import net.ginteam.carmen.kotlin.model.UserModel
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

interface AuthProvider {

    var currentCachedUser: UserModel?

    fun userLogin(email: String, password: String): Observable <ResponseModel <AuthModel>>
    fun userRegister(name: String, email: String, password: String): Observable <ResponseModel <AuthModel>>
    fun fetchCurrentUser(): Observable <ResponseModel <UserModel>>

}

object AuthenticationProvider : AuthProvider {
    private val authService: AuthService = AuthService.create()

    override var currentCachedUser: UserModel? = null

    override fun userLogin(email: String, password: String): Observable <ResponseModel <AuthModel>> {
        return authService
                .userLogin(email, password)
                .asyncWithCache(false)
                .doOnNext {
                    cacheUser(it.data.user)
                    saveUserAccessToken(it.data.token)
                }
    }

    override fun userRegister(name: String, email: String, password: String): Observable <ResponseModel <AuthModel>> {
        return authService
                .userRegister(name, email, password)
                .asyncWithCache(false)
                .doOnNext {
                    cacheUser(it.data.user)
                    saveUserAccessToken(it.data.token)
                }
    }

    override fun fetchCurrentUser(): Observable <ResponseModel <UserModel>> {
        return authService
                .getCurrentUser()
                .asyncWithCache()
                .doOnNext {
                    cacheUser(it.data)
                }
    }

    private fun cacheUser(user: UserModel) {
        currentCachedUser = user
    }

    private fun saveUserAccessToken(token: String) {
        SharedPreferencesManager.userAccessToken = token
    }
}