package net.ginteam.carmen.kotlin.view.adapter.news

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
import net.ginteam.carmen.kotlin.model.NewsModel

/**
 * Created by eugene_shcherbinock on 3/7/17.
 */

abstract class BaseNewsAdapter(protected val news: MutableList <NewsModel>,
                               val onNewsItemClick: (NewsModel) -> Unit,
                               val onFavoriteClick: (NewsModel, Boolean) -> Unit)
    : RecyclerView.Adapter <RecyclerView.ViewHolder>() {

    fun invalidateNewsItem(newsItem: NewsModel) {
        // we can try invalidate news item that out of memory
        // and search it by id
        val invalidatingNewsItem: NewsModel = news.find { newsItem.id == it.id }!!
        notifyItemChanged(news.indexOf(invalidatingNewsItem))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val newsItemViewHolder: ViewHolder? = holder as ViewHolder?
        with(newsItemViewHolder?.mImageViewNewsPhoto!!.layoutParams) {
            width = calculatePhotoSize()
            height = calculatePhotoSize()
        }
        newsItemViewHolder?.mImageViewNewsPhoto.requestLayout()
        newsItemViewHolder?.bindData(news[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return LayoutInflater.from(parent?.context)
                .inflate(getItemLayoutResId(), parent, false).let {
            it.layoutParams = RecyclerView.LayoutParams(calculateItemWidth(), RecyclerView.LayoutParams.WRAP_CONTENT)
            ViewHolder(it, onNewsItemClick, onFavoriteClick)
        }
    }

    override fun getItemCount(): Int = news.size

    @LayoutRes
    protected abstract fun getItemLayoutResId(): Int

    protected abstract fun calculateItemWidth(): Int
    protected abstract fun calculatePhotoSize(): Int

    open class ViewHolder(itemView: View,
                          val onClick: (NewsModel) -> Unit,
                          val onFavoriteClick: (NewsModel, Boolean) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val mImageViewNewsPhoto = itemView.findViewById(R.id.image_view_news_photo) as ImageView
        private val mTextViewNewsName = itemView.findViewById(R.id.text_view_news_name) as TextView
        private val mTextViewNewsDate = itemView.findViewById(R.id.text_view_news_date) as TextView
        private val mTextViewNewsSource : TextView? = itemView.findViewById(R.id.text_view_news_source) as TextView?
        private val mTextViewNewsViewsCount = itemView.findViewById(R.id.text_view_news_views_count) as TextView
        private val mImageButtonAddToFavorite: ImageButton
                = itemView.findViewById(R.id.image_button_company_favorite) as ImageButton

        fun bindData(newsItem: NewsModel) {
            with(newsItem) {
                mTextViewNewsName.text = title
                mTextViewNewsDate.text = publicationDate.substring(0, publicationDate.indexOf(' '))
                mTextViewNewsSource?.text = source
                if (image.isNotEmpty()) {
                    Picasso
                            .with(CarmenApplication.getContext())
                            .load(image)
                            .placeholder(R.drawable.ic_default_photo)
                            .into(mImageViewNewsPhoto)
                }
                mImageButtonAddToFavorite.setImageResource(if (isFavorite) {
                    R.drawable.ic_company_favorite_enable
                } else {
                    R.drawable.ic_company_favorite_disable
                })

                transitionViewHolder = this@ViewHolder

                itemView.setOnClickListener { onClick(this) }
                mImageButtonAddToFavorite.setOnClickListener { onFavoriteClick(this, !isFavorite) }
            }
        }
    }

}