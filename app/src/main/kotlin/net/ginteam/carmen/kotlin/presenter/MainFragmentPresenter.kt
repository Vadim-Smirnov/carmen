package net.ginteam.carmen.kotlin.presenter

import net.ginteam.carmen.kotlin.contract.MainFragmentContract
import net.ginteam.carmen.kotlin.provider.AuthProvider
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */

// TODO check
fun isNetworkAvailable(): Boolean {
    return true
}

class MainFragmentPresenter : BasePresenter <MainFragmentContract.View>(), MainFragmentContract.Presenter {

    private val mAuthProvider: AuthProvider = AuthenticationProvider

    override fun prepareFragments() {
        // check connection before loading
        // it's necessary for showing only one error dialog instead of three
        if (isNetworkAvailable()) {
            mView?.showCategoriesFragment()
            mView?.showPopularCompaniesFragment()
            updateRecentlyWatchedCompaniesFragmentIfExists()
            return
        }
        // show network error
        mView?.showError("", isNetworkError = true)
    }

    override fun updateRecentlyWatchedCompaniesFragmentIfExists() {
        mAuthProvider.currentCachedUser?.let {
            mView?.showRecentlyWatchedCompaniesFragment()
        }
    }
}