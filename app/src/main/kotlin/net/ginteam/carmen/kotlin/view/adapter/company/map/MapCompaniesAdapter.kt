package net.ginteam.carmen.kotlin.view.adapter.company.map

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.kotlin.view.adapter.company.BaseCompaniesAdapter
import net.ginteam.carmen.kotlin.view.adapter.company.HorizontalCompaniesAdapter
import net.ginteam.carmen.utils.DisplayUtils

/**
 * Created by eugene_shcherbinock on 2/21/17.
 */

class MapCompaniesAdapter(companies: MutableList <CompanyModel>,
                          onCompanyItemClick: (CompanyModel) -> Unit,
                          onFavoriteClick: (CompanyModel, Boolean) -> Unit)
    : HorizontalCompaniesAdapter(companies, onCompanyItemClick, onFavoriteClick) {

    enum class ITEM_TYPE {
        COMPANY, EMPTY
    }

    override var VISIBLE_ITEMS_COUNT: Int = 2

    init {
        companies.add(CompanyModel())
    }

    fun getCompany(position: Int) = companies[position]

    fun updateCompanyItem(company: CompanyModel): Int {
        val changedPosition: Int = companies.indexOf(company)
        notifyItemChanged(changedPosition)
        return changedPosition
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) != ITEM_TYPE.COMPANY.ordinal) {
            return
        }

        val companyViewHolder: ViewHolder? = holder as ViewHolder?
        companyViewHolder?.mImageViewPhoto!!.layoutParams.height = calculatePhotoSize()
        companyViewHolder.mImageViewPhoto.requestLayout()
        companyViewHolder.bindData(companies[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE.COMPANY.ordinal) {
            LayoutInflater.from(parent?.context)
                    .inflate(getItemLayoutResId(), parent, false).let {
                it.layoutParams = RecyclerView.LayoutParams(calculateItemWidth(), RecyclerView.LayoutParams.WRAP_CONTENT)
                ViewHolder(it, onCompanyItemClick, onFavoriteClick)
            }
        } else {
            LayoutInflater.from(parent?.context)
                    .inflate(R.layout.list_item_company_map_empty, parent, false).let {
                it.layoutParams = RecyclerView.LayoutParams(calculateItemWidth(), RecyclerView.LayoutParams.WRAP_CONTENT)
                EmptyViewHolder(it)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == companies.size - 1) {
            ITEM_TYPE.EMPTY.ordinal
        } else {
            ITEM_TYPE.COMPANY.ordinal
        }
    }

    override fun getItemLayoutResId(): Int = R.layout.list_item_company_map

    override fun calculateItemWidth(): Int {
        val screenSize = DisplayUtils.getScreenSize(CarmenApplication.getContext())
        val itemSpacing = CarmenApplication.getContext().resources.getDimension(R.dimen.vertical_list_item_spacing).toInt()
        return screenSize.x / VISIBLE_ITEMS_COUNT - itemSpacing
    }

    override fun calculatePhotoSize(): Int {
        return calculateItemWidth() * 64 / 100
    }

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
                Color.TRANSPARENT
            })
        }
    }

    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.visibility = View.GONE
        }
    }

}