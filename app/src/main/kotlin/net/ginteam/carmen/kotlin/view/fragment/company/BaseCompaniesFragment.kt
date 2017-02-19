package net.ginteam.carmen.kotlin.view.fragment.company

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.view.adapter.company.BaseCompaniesAdapter
import net.ginteam.carmen.kotlin.view.fragment.BaseFragment

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

open abstract class BaseCompaniesFragment<E : BaseCompaniesAdapter, in V : BaseCompaniesContract.View,
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

    override fun invoke(company: CompanyModel, isFavorite: Boolean) {
        Log.d("BaseCompaniesFragment", "Is company favorite? - $isFavorite")
        if (isFavorite) {
            mPresenter.addCompanyToFavorites(company)
        } else {
            mPresenter.removeCompanyFromFavorites(company)
        }
    }

    override fun invoke(company: CompanyModel) {
        mCompanySelectedListener?.onCompanySelected(company)
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