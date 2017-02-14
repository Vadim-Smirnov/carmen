package net.ginteam.carmen.kotlin.provider

import net.ginteam.carmen.kotlin.api.service.AuthService
import net.ginteam.carmen.kotlin.async
import net.ginteam.carmen.kotlin.model.AuthModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import net.ginteam.carmen.kotlin.model.UserModel
import rx.Observable

/**
 * Created by eugene_shcherbinock on 2/10/17.
 */

interface AuthProvider {

    fun userLogin(email: String, password: String): Observable <ResponseModel <AuthModel>>
    fun userRegister(name: String, email: String, password: String): Observable <ResponseModel <AuthModel>>
    fun fetchCurrentUser(): Observable <ResponseModel <UserModel>>

}

object AuthenticationProvider : AuthProvider {
    private val authService: AuthService = AuthService.create()

    var currentCachedUser: UserModel? = null
        private set

    override fun userLogin(email: String, password: String): Observable <ResponseModel <AuthModel>> {
        return authService
                .userLogin(email, password)
                .async()
                .doOnNext {
                    cacheUser(it.data.user)
                }
    }

    override fun userRegister(name: String, email: String, password: String): Observable <ResponseModel <AuthModel>> {
        return authService
                .userRegister(name, email, password)
                .async()
                .doOnNext {
                    cacheUser(it.data.user)
                }
    }

    override fun fetchCurrentUser(): Observable <ResponseModel <UserModel>> {
        return authService
                .getCurrentUser()
                .async()
                .doOnNext {
                    cacheUser(it.data)
                }
    }

    private fun cacheUser(user: UserModel) {
        currentCachedUser = user
    }
}