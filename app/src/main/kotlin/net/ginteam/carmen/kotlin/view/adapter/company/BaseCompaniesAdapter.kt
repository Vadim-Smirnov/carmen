package net.ginteam.carmen.kotlin.view.adapter.company

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.CompanyModel
import net.ginteam.carmen.view.custom.rating.CarmenRatingView

/**
 * Created by eugene_shcherbinock on 2/16/17.
 */
abstract class BaseCompaniesAdapter(protected val companies: MutableList <CompanyModel>,
                                    val onCompanyItemClick: (CompanyModel) -> Unit,
                                    val onFavoriteClick: (CompanyModel, Boolean) -> Unit)
    : RecyclerView.Adapter <RecyclerView.ViewHolder>() {

    fun invalidateCompany(company: CompanyModel) {
        // we can try invalidate company that out of memory
        // and search it by id
        val invalidatingCompany: CompanyModel = companies.find { company.id == it.id }!!
        notifyItemChanged(companies.indexOf(invalidatingCompany))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val companyViewHolder: ViewHolder? = holder as ViewHolder?
        with(companyViewHolder?.mImageViewPhoto!!.layoutParams) {
            width = calculatePhotoSize()
            height = calculatePhotoSize()
        }
        companyViewHolder?.mImageViewPhoto.requestLayout()
        companyViewHolder?.bindData(companies[position])
    }

    override fun getItemCount(): Int = companies.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return LayoutInflater.from(parent?.context)
                .inflate(getItemLayoutResId(), parent, false).let {
            it.layoutParams = RecyclerView.LayoutParams(calculateItemWidth(), RecyclerView.LayoutParams.WRAP_CONTENT)
            ViewHolder(it, onCompanyItemClick, onFavoriteClick)
        }
    }

    @LayoutRes
    protected abstract fun getItemLayoutResId(): Int

    protected abstract fun calculateItemWidth(): Int
    protected abstract fun calculatePhotoSize(): Int

    open class ViewHolder(itemView: View,
                          val onClick: (CompanyModel) -> Unit,
                          val onFavoriteClick: (CompanyModel, Boolean) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val mImageViewPhoto: ImageView =
                itemView.findViewById(R.id.image_view_company_photo) as ImageView
        private val mTextViewName: TextView
                = itemView.findViewById(R.id.text_view_company_name) as TextView
        private val mTextViewAddress: TextView?
                = itemView.findViewById(R.id.text_view_company_address) as TextView?
        private val mRatingViewRating: CarmenRatingView
                = itemView.findViewById(R.id.rating_view_company) as CarmenRatingView
        private val mRatingViewPrice: CarmenRatingView?
                = itemView.findViewById(R.id.rating_view_company_price) as CarmenRatingView?
        private val mImageButtonAddToFavorite: ImageButton
                = itemView.findViewById(R.id.image_button_company_favorite) as ImageButton
        private val mTextViewDistance: TextView?
                = itemView.findViewById(R.id.text_view_company_distance) as TextView?

        open fun bindData(company: CompanyModel) {
            with(company) {
                mTextViewName.text = name
                mTextViewAddress?.text = address
                mRatingViewRating.rating = rating.toFloat()
                mRatingViewPrice?.rating = price.toFloat()

                val locationMeasure = CarmenApplication.getContext().getString(R.string.location_measure)
                mTextViewDistance?.text = if (distance == 0.0) {
                    "? $locationMeasure"
                } else {
                    String.format("%.1f $locationMeasure", distance / 1000)
                }
                mImageButtonAddToFavorite.setImageResource(if (isFavorite) {
                    R.drawable.ic_company_favorite_enable
                } else {
                    R.drawable.ic_company_favorite_disable
                })

                mImageViewPhoto.setImageResource(R.drawable.ic_default_photo)
                pictures?.let {
                    if (!it.isEmpty()) {
                        val imageUrl = it.first()
                        if (!imageUrl.isEmpty()) {
                            Picasso
                                    .with(CarmenApplication.getContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.ic_default_photo)
                                    .into(mImageViewPhoto)
                        }
                    }
                }
                transitionView = this@ViewHolder

                itemView.setOnClickListener { onClick(this) }
                mImageButtonAddToFavorite.setOnClickListener { onFavoriteClick(this, !isFavorite) }
            }
        }
    }

}