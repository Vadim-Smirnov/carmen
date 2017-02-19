package net.ginteam.carmen.kotlin.view.fragment.company

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.FavoriteCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.presenter.company.FavoritesPresenter
import net.ginteam.carmen.kotlin.view.adapter.company.FavoriteCompaniesAdapter
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListVerticalItemDecorator

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */
class FavoritesFragment
    : BaseCompaniesFragment <FavoriteCompaniesAdapter, FavoriteCompaniesContract.View, FavoriteCompaniesContract.Presenter>(),
        FavoriteCompaniesContract.View {

    override var mPresenter: FavoriteCompaniesContract.Presenter = FavoritesPresenter()

    override lateinit var mCompaniesAdapter: FavoriteCompaniesAdapter

    companion object {
        private const val SNACKBAR_DURATION_SHOW_MILLISECONDS: Int = 5000

        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }

    override fun showCompanies(companies: MutableList<CompanyModel>, pagination: PaginationModel?) {
        mCompaniesAdapter = FavoriteCompaniesAdapter(companies, this, this)
        mRecyclerViewCompanies.adapter = mCompaniesAdapter
    }

    override fun showFavoriteConfirmationMessage(company: CompanyModel, messageResId: Int) {
        val snackbar: Snackbar = Snackbar.make(mFragmentView, getString(messageResId), Snackbar.LENGTH_LONG)
        // if company had been removed from favorites
        if (!company.isFavorite) {
            // remove from list adapter
            mCompaniesAdapter.removeCompany(company)

            // set snackbar action to restore
            snackbar.setAction(R.string.undo_company_removed_message) {
                // add company to favorites
                mPresenter.addCompanyToFavorites(company)
            }
            snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.colorApplicationBlue))
            snackbar.duration = SNACKBAR_DURATION_SHOW_MILLISECONDS
        } else {
            // if company restored to favorites insert it in adapter
            mCompaniesAdapter.restoreRemovedCompany()
        }
        snackbar.show()
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