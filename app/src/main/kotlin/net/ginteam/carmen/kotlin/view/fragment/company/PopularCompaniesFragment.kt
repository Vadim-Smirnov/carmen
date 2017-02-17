package net.ginteam.carmen.kotlin.view.fragment.company

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.PopularCompaniesContract
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.model.PaginationModel
import net.ginteam.carmen.kotlin.presenter.company.PopularCompaniesPresenter
import net.ginteam.carmen.kotlin.view.adapter.company.BaseCompaniesAdapter
import net.ginteam.carmen.kotlin.view.adapter.company.HorizontalCompaniesAdapter
import net.ginteam.carmen.kotlin.view.adapter.company.VerticalCompaniesAdapter
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListHorizontalItemDecorator
import net.ginteam.carmen.view.adapter.company.CompanyRecyclerListVerticalItemDecorator

/**
 * Created by eugene_shcherbinock on 2/17/17.
 */
class PopularCompaniesFragment
    : BaseCompaniesFragment <BaseCompaniesAdapter, PopularCompaniesContract.View, PopularCompaniesContract.Presenter>(),
        PopularCompaniesContract.View {

    override var mPresenter: PopularCompaniesContract.Presenter = PopularCompaniesPresenter()

    private var isHorizontal: Boolean = false
    override lateinit var mCompaniesAdapter: BaseCompaniesAdapter

    companion object {
        const val ORIENTATION_ARGUMENT = "orientation"

        fun newInstance(isHorizontal: Boolean): PopularCompaniesFragment {
            val bundle = Bundle()
            bundle.putBoolean(ORIENTATION_ARGUMENT, isHorizontal)

            val instance = PopularCompaniesFragment()
            instance.arguments = bundle
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isHorizontal = arguments.getBoolean(ORIENTATION_ARGUMENT)
    }

    override fun showCompanies(companies: MutableList<CompanyModel>, pagination: PaginationModel?) {
        mCompaniesAdapter = if (isHorizontal) {
            HorizontalCompaniesAdapter(companies, this, this)
        } else {
            VerticalCompaniesAdapter(companies, this, this)
        }
        mRecyclerViewCompanies.adapter = mCompaniesAdapter
    }

    override fun getLayoutResId(): Int = R.layout.fragment_company_list

    override fun fetchCompanies() {
        mPresenter.fetchPopularCompanies()
    }

    override fun getRecyclerViewItemDecorator(): RecyclerView.ItemDecoration {
        return if (isHorizontal) {
            CompanyRecyclerListHorizontalItemDecorator(context, R.dimen.company_item_spacing)
        } else {
            CompanyRecyclerListVerticalItemDecorator(context, R.dimen.company_item_spacing)
        }
    }

    override fun getRecyclerViewLayoutManager(): LinearLayoutManager {
        return if (isHorizontal) {
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        } else {
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}