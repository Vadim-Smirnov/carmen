package net.ginteam.carmen.kotlin.presenter

import io.realm.Realm
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.MainActivityContract
import net.ginteam.carmen.kotlin.manager.PreferencesManager
import net.ginteam.carmen.kotlin.manager.SharedPreferencesManager
import net.ginteam.carmen.kotlin.model.UserModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.provider.AuthProvider
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider

/**
 * Created by eugene_shcherbinock on 2/15/17.
 */
class MainActivityPresenter : BasePresenter <MainActivityContract.View>(), MainActivityContract.Presenter {

    private val mAuthProvider: AuthProvider = AuthenticationProvider
    private val mPreferences: PreferencesManager = SharedPreferencesManager

    override fun prepareNavigationViewForUserStatus() {
        val currentUser: UserModel? = mAuthProvider.currentCachedUser
        if (currentUser != null) {
            mView?.inflateNavigationView(R.menu.navigation_menu_full, R.layout.navigation_view_user_header)
            mView?.showUserInformation(currentUser)
            return
        }
        mView?.inflateNavigationView(R.menu.navigation_menu_short, R.layout.navigation_view_default_header)
    }

    override fun fetchCosts() {
        val realmInstance: Realm = Realm.getDefaultInstance()
        realmInstance
                .where(CostTypeModel::class.java)
                .findAllAsync()
                .asObservable()
                .subscribe { mView?.showCosts(it) }
    }

    override fun isUserSignedIn(): Boolean = mAuthProvider.currentCachedUser != null

    override fun getUserCityName(): String = mPreferences.userCityModel!!.name

    override fun localUserLogout() {
        mAuthProvider.currentCachedUser = null
        mPreferences.userAccessToken = ""

        mView?.showSignInActivity()
    }

}