package net.ginteam.carmen.kotlin.view.fragment.company

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.view.adapter.company.BaseCompaniesAdapter
import net.ginteam.carmen.kotlin.view.fragment.BaseFragment

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */
abstract class BaseCompaniesFragment<E : BaseCompaniesAdapter, in V : BaseCompaniesContract.View,
        T : BaseCompaniesContract.Presenter <V>>
    : BaseFragment <V, T>(), BaseCompaniesContract.View,
        (CompanyModel) -> Unit, (CompanyModel, Boolean) -> Unit {

    protected var mCompanySelectedListener: OnCompanySelectedListener? = null

    protected lateinit var mLayoutManager: LinearLayoutManager
    protected lateinit var mRecyclerViewCompanies: RecyclerView
    protected abstract var mCompaniesAdapter: E

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCompanySelectedListener = context as OnCompanySelectedListener?
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCompanies()
    }

    override fun getNetworkErrorAction(): (() -> Unit)? = {
        fetchCompanies()
    }

    /**
     * onCompanyItemClick implementation for {@link BaseCompaniesAdapter}
     */
    override fun invoke(company: CompanyModel) {
        mCompanySelectedListener?.onCompanySelected(company)
    }

    /**
     * onAddToFavoriteClick implementation for {@link BaseCompaniesAdapter}
     */
    override fun invoke(company: CompanyModel, isFavorite: Boolean) {
        if (isFavorite) {
            mPresenter.addCompanyToFavorites(company)
        } else {
            mPresenter.removeCompanyFromFavorites(company)
        }
    }

    override fun showFavoriteConfirmationMessage(company: CompanyModel, messageResId: Int) {
        mCompaniesAdapter.invalidateCompany(company)

        Snackbar.make(activity.findViewById(R.id.main_fragment_container) ?: mFragmentView,
                getString(messageResId), Snackbar.LENGTH_LONG).show()
    }

    override fun showFavoriteErrorMessage(messageResId: Int) {
        Snackbar.make(activity.findViewById(R.id.main_fragment_container) ?: mFragmentView,
                getString(messageResId), Snackbar.LENGTH_LONG).show()
    }

    protected abstract fun fetchCompanies()

    protected abstract fun getRecyclerViewItemDecorator(): RecyclerView.ItemDecoration
    protected abstract fun getRecyclerViewLayoutManager(): LinearLayoutManager

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        mRecyclerViewCompanies = mFragmentView.findViewById(R.id.recycler_view_companies) as RecyclerView
        mRecyclerViewCompanies.addItemDecoration(getRecyclerViewItemDecorator())
        mLayoutManager = getRecyclerViewLayoutManager()
        mRecyclerViewCompanies.layoutManager = mLayoutManager
    }

    interface OnCompanySelectedListener {

        fun onCompanySelected(company: CompanyModel)

    }

}