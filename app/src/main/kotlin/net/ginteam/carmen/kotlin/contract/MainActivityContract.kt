package net.ginteam.carmen.kotlin.contract

import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import net.ginteam.carmen.kotlin.model.UserModel

/**
 * Created by eugene_shcherbinock on 2/15/17.
 */
object MainActivityContract {

    interface View : BaseContract.View {

        fun inflateNavigationView(@MenuRes menuResId: Int, @LayoutRes headerLayoutResId: Int)
        fun showUserInformation(user: UserModel)

        fun showSignInActivity()

    }

    interface Presenter : BaseContract.Presenter <View> {

        fun prepareNavigationViewForUserStatus()
        fun isUserSignedIn(): Boolean
        fun getUserCityName(): String
        fun localUserLogout()

    }

}