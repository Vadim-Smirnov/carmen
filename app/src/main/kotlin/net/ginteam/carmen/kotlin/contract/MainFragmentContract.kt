package net.ginteam.carmen.kotlin.contract

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */
object MainFragmentContract {

    interface View : BaseContract.View {

        fun showCategoriesFragment()
        fun showRecentlyWatchedCompaniesFragment()
        fun showPopularCompaniesFragment()

    }

    interface Presenter : BaseContract.Presenter <View> {

        fun prepareFragments()
        fun updateRecentlyWatchedCompaniesFragmentIfExists()

    }

}