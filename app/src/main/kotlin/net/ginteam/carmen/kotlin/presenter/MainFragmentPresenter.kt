package net.ginteam.carmen.kotlin.presenter

import net.ginteam.carmen.kotlin.contract.MainFragmentContract
import net.ginteam.carmen.kotlin.provider.AuthProvider
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */
class MainFragmentPresenter : BasePresenter <MainFragmentContract.View>(), MainFragmentContract.Presenter {

    private val mAuthProvider: AuthProvider = AuthenticationProvider

    override fun prepareFragments() {
        mView?.showCategoriesFragment()
        mView?.showPopularCompaniesFragment()
    }

    override fun updateRecentlyWatchedCompaniesFragmentIfExists() {
        mAuthProvider.currentCachedUser?.let {
            mView?.showRecentlyWatchedCompaniesFragment()
        }
    }
}