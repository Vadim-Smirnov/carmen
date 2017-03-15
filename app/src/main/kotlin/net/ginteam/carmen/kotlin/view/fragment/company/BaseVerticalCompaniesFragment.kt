package net.ginteam.carmen.kotlin.view.fragment.company

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.BaseCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.view.adapter.company.VerticalCompaniesAdapter
import net.ginteam.carmen.kotlin.view.fragment.news.BasePaginatableCompaniesFragment
import net.ginteam.carmen.view.adapter.RecyclerListVerticalItemDecorator

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

abstract class BaseVerticalCompaniesFragment<E : VerticalCompaniesAdapter, in V : BaseCompaniesContract.View,
        T : BaseCompaniesContract.Presenter <V>> : BasePaginatableCompaniesFragment<E, V, T>() {

    override fun getLayoutResId(): Int = R.layout.fragment_company_list

    override fun initializeAdapter(company: MutableList<CompanyModel>,
                                   onItemClick: (CompanyModel) -> Unit,
                                   onFavoriteClick: (CompanyModel, Boolean) -> Unit
    ): E = VerticalCompaniesAdapter(company, onItemClick, onFavoriteClick) as E

    override fun getRecyclerViewItemDecorator(): RecyclerView.ItemDecoration
            = RecyclerListVerticalItemDecorator(context, R.dimen.vertical_list_item_spacing)

    override fun getRecyclerViewLayoutManager(): LinearLayoutManager
            = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}