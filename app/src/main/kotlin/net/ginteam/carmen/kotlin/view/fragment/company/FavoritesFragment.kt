package net.ginteam.carmen.kotlin.view.fragment.company

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.FavoriteCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.presenter.company.list.FavoritesPresenter
import net.ginteam.carmen.kotlin.view.adapter.company.FavoriteCompaniesAdapter

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */
class FavoritesFragment : BaseVerticalCompaniesFragment<FavoriteCompaniesAdapter,
        FavoriteCompaniesContract.View, FavoriteCompaniesContract.Presenter>(),
        FavoriteCompaniesContract.View {

    override var mPresenter: FavoriteCompaniesContract.Presenter = FavoritesPresenter()

    companion object {
        private const val SNACKBAR_DURATION_SHOW_MILLISECONDS: Int = 5000
        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }

    override fun initializeAdapter(company: MutableList<CompanyModel>,
                                   onItemClick: (CompanyModel) -> Unit,
                                   onFavoriteClick: (CompanyModel, Boolean) -> Unit
    ): FavoriteCompaniesAdapter = FavoriteCompaniesAdapter(company, onItemClick, onFavoriteClick)

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

    override fun fetchCompanies() {
        mPresenter.fetchUserFavorites(mCurrentPaginationPage)
    }

}