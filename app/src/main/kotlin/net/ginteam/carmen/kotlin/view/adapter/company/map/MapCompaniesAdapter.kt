package net.ginteam.carmen.kotlin.view.adapter.company.map

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.view.adapter.company.BaseCompaniesAdapter
import net.ginteam.carmen.kotlin.view.adapter.company.HorizontalCompaniesAdapter

/**
 * Created by eugene_shcherbinock on 2/21/17.
 */

class MapCompaniesAdapter(companies: MutableList <CompanyModel>,
                          onCompanyItemClick: (CompanyModel) -> Unit,
                          onFavoriteClick: (CompanyModel, Boolean) -> Unit)
    : HorizontalCompaniesAdapter(companies, onCompanyItemClick, onFavoriteClick) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val companyViewHolder: ViewHolder? = holder as ViewHolder?
        with(companyViewHolder?.mImageViewPhoto!!.layoutParams) {
            width = calculatePhotoSize()
            height = calculatePhotoSize()
        }
        companyViewHolder?.mImageViewPhoto.requestLayout()
        companyViewHolder?.bindData(companies[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return LayoutInflater.from(parent?.context)
                .inflate(getItemLayoutResId(), parent, false).let {
            it.layoutParams = RecyclerView.LayoutParams(calculateItemWidth(), RecyclerView.LayoutParams.WRAP_CONTENT)
            ViewHolder(it, onCompanyItemClick, onFavoriteClick)
        }
    }

    override fun getItemLayoutResId(): Int = R.layout.list_item_company_map

    class ViewHolder(itemView: View,
                     onClick: (CompanyModel) -> Unit,
                     onFavoriteClick: (CompanyModel, Boolean) -> Unit)
        : BaseCompaniesAdapter.ViewHolder(itemView, onClick, onFavoriteClick) {

        private val mViewSelectionIndicator: View
                = itemView.findViewById(R.id.view_selection_indicator)

        override fun bindData(company: CompanyModel) {
            super.bindData(company)

            mViewSelectionIndicator.setBackgroundColor(if (company.isSelected) {
                Color.BLACK
            } else {
                Color.WHITE
            })
        }
    }

}