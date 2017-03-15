package net.ginteam.carmen.kotlin.view.adapter.company

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.view.adapter.LoadingViewHolder
import net.ginteam.carmen.utils.DisplayUtils

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */

abstract class PaginatableCompaniesAdapter(companies: MutableList <CompanyModel>,
                                  onCompanyItemClick: (CompanyModel) -> Unit,
                                  onFavoriteClick: (CompanyModel, Boolean) -> Unit)
    : BaseCompaniesAdapter(companies, onCompanyItemClick, onFavoriteClick) {

    enum class ITEM_TYPE {
        COMPANY, LOADING
    }

    private var isShowLoadingIndicator: Boolean = false

    fun addCompanies(companies: List <CompanyModel>) {
        val insertPosition = this.companies.size
        this.companies.addAll(companies)
        notifyItemRangeInserted(insertPosition, companies.size)
    }

    fun showLoading() {
        isShowLoadingIndicator = true
        companies.add(CompanyModel())
        notifyItemInserted(companies.size - 1)
    }

    fun hideLoading() {
        isShowLoadingIndicator = false
        val position = companies.size - 1
        companies.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE.COMPANY.ordinal) {
            super.onCreateViewHolder(parent, viewType)
        } else {
            val loadingView = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.list_item_progress, parent, false)
            return LoadingViewHolder(loadingView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) != ITEM_TYPE.COMPANY.ordinal) {
            return
        }
        super.onBindViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == companies.size - 1 && isShowLoadingIndicator) {
            ITEM_TYPE.LOADING.ordinal
        } else {
            ITEM_TYPE.COMPANY.ordinal
        }
    }

}