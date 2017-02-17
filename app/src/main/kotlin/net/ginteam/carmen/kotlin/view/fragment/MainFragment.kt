package net.ginteam.carmen.kotlin.view.fragment

import android.os.Bundle
import android.view.View
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.MainFragmentContract
import net.ginteam.carmen.kotlin.prepareFragment
import net.ginteam.carmen.kotlin.presenter.MainFragmentPresenter
import net.ginteam.carmen.kotlin.view.fragment.category.CategoriesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.PopularCompaniesFragment
import net.ginteam.carmen.kotlin.view.fragment.company.RecentlyWatchedCompaniesFragment

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */

class MainFragment : BaseFragment <MainFragmentContract.View, MainFragmentContract.Presenter>(),
        MainFragmentContract.View {

    override var mPresenter: MainFragmentContract.Presenter = MainFragmentPresenter()

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.prepareFragments()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.updateRecentlyWatchedCompaniesFragmentIfExists()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_main

    override fun showCategoriesFragment() {
        prepareFragment(R.id.categories_fragment_container, CategoriesFragment.newInstance(false))
    }

    override fun showPopularCompaniesFragment() {
        prepareFragment(R.id.popular_companies_fragment_container, PopularCompaniesFragment.newInstance(true))
    }

    override fun showRecentlyWatchedCompaniesFragment() {
        prepareFragment(R.id.recently_companies_fragment_container, RecentlyWatchedCompaniesFragment.newInstance(true))
    }

}