package net.ginteam.carmen.kotlin.view.adapter.category

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.imageResourceId
import net.ginteam.carmen.kotlin.model.CategoryModel

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */
class CategoriesAdapter(@LayoutRes private val itemLayoutResId: Int,
                        private val categories: List <CategoryModel>,
                        val onCategoryItemClick: (CategoryModel) -> Unit)
    : RecyclerView.Adapter <CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent?.context)
                .inflate(itemLayoutResId, parent, false).let {
            ViewHolder(it, onCategoryItemClick)
        }
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(categories[position])
    }

    class ViewHolder(itemView: View, val onClick: (CategoryModel) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val mTextViewCategoryName: TextView
                = itemView.findViewById(R.id.text_view_category_name) as TextView
        private val mTextViewItemsCount: TextView
                = itemView.findViewById(R.id.text_view_category_items_count) as TextView
        private val mImageViewIcon: ImageView
                = itemView.findViewById(R.id.image_view_category_icon) as ImageView

        fun bindData(category: CategoryModel) {
            with(category) {
                mTextViewCategoryName.text = name
                mImageViewIcon.setImageResource(imageResourceId())
                mTextViewItemsCount.text = CarmenApplication.getContext().resources
                        .getQuantityString(
                                R.plurals.category_items_count_string,
                                companiesCount,
                                companiesCount)

                itemView.setOnClickListener {
                    onClick(this)
                }
            }
        }

    }

}