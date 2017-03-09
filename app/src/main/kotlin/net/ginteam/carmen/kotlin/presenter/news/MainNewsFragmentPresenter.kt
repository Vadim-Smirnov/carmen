package net.ginteam.carmen.kotlin.presenter.news

import net.ginteam.carmen.kotlin.contract.MainNewsFragmentContract
import net.ginteam.carmen.kotlin.presenter.BasePresenter
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider

/**
 * Created by vadik on 09.03.17.
 */
class MainNewsFragmentPresenter : BasePresenter<MainNewsFragmentContract.View>(), MainNewsFragmentContract.Presenter {


    override fun isUserSignedIn(): Boolean  = AuthenticationProvider.currentCachedUser != null

}