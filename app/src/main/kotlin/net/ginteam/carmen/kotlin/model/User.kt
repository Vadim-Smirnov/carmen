package net.ginteam.carmen.kotlin.model

/**
 * Created by eugene_shcherbinock on 2/9/17.
 */

data class AuthModel(val user: UserModel,
                     val token: String)

data class UserModel(val id: Int,
                     val name: String,
                     val email: String)