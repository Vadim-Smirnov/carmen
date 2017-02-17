package net.ginteam.carmen.kotlin.view.fragment.company

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.FavoriteCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.presenter.company.FavoritesPresenter
import net.ginteam.carmen.kotlin.view.adapter.company.VerticalCompaniesAdapter
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListVerticalItemDecorator

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */
class FavoritesFragment
    : BaseCompaniesFragment <VerticalCompaniesAdapter, FavoriteCompaniesContract.View, FavoriteCompaniesContract.Presenter>(),
        FavoriteCompaniesContract.View {

    override var mPresenter: FavoriteCompaniesContract.Presenter = FavoritesPresenter()

    override lateinit var mCompaniesAdapter: VerticalCompaniesAdapter

    companion object {
        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }

    override fun showCompanies(companies: MutableList<CompanyModel>, pagination: PaginationModel?) {
        mCompaniesAdapter = VerticalCompaniesAdapter(companies, this, this)
        mRecyclerViewCompanies.adapter = mCompaniesAdapter
    }

    override fun getLayoutResId(): Int = R.layout.fragment_company_list

    override fun fetchCompanies() {
        mPresenter.fetchUserFavorites()
    }

    override fun getRecyclerViewItemDecorator(): RecyclerView.ItemDecoration
            = CompanyRecyclerListVerticalItemDecorator(context, R.dimen.company_item_spacing)

    override fun getRecyclerViewLayoutManager(): LinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}