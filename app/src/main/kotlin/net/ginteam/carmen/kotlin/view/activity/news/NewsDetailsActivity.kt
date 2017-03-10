package net.ginteam.carmen.kotlin.view.activity.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.ginteam.carmen.CarmenApplication
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.NewsDetailsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.prepareFragment
import net.ginteam.carmen.kotlin.presenter.news.NewsDetailsPresenter
import net.ginteam.carmen.kotlin.view.activity.BaseActivity
import net.ginteam.carmen.kotlin.view.activity.authentication.SignInActivity
import net.ginteam.carmen.kotlin.view.fragment.news.BaseNewsFragment
import net.ginteam.carmen.kotlin.view.fragment.news.PopularNewsHorizontalFragment
import net.ginteam.carmen.utils.DeviceUtils

/**
 * Created by vadik on 10.03.17.
 */

class NewsDetailsActivity : BaseActivity<NewsDetailsContract.View, NewsDetailsContract.Presenter>(),
        NewsDetailsContract.View, BaseNewsFragment.OnNewsItemSelectedListener {

    override var mPresenter: NewsDetailsContract.Presenter = NewsDetailsPresenter()

    private lateinit var mSelectedNewsItem: NewsModel

    private var mOptionsMenu: Menu? = null

    companion object {
        const val NEWS_ARGUMENT = "news"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()

        mPresenter.fetchNewsDetail(mSelectedNewsItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            mOptionsMenu = it
            menuInflater.inflate(R.menu.company_detail_menu, mOptionsMenu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_favorite -> {
                if (userHaveAccess()) {
                    if (mSelectedNewsItem.isFavorite) {
                        mPresenter.removeNewsFromFavorites(mSelectedNewsItem)
                    } else {
                        mPresenter.addNewsToFavorites(mSelectedNewsItem)
                    }
                }
            }
            else -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SignInActivity.SIGN_IN_REQUEST_CODE -> mPresenter.fetchNewsDetail(mSelectedNewsItem)
            }
        }
    }

    override fun showLoading(show: Boolean, messageResId: Int) {
        super.showLoading(show, messageResId)
        findViewById(R.id.layout_content).visibility = if (show) View.GONE else View.VISIBLE
    }

    override fun getLayoutResId(): Int = R.layout.activity_news_detail

    override fun onNewsItemSelected(newsItem: NewsModel) {
        val intent = Intent(getContext(), NewsDetailsActivity::class.java)
        intent.putExtra(NewsDetailsActivity.NEWS_ARGUMENT, newsItem)

        if (DeviceUtils.hasLollipop()) {
            newsItem.transitionViewHolder?.let {
                val newsPhotoPair: Pair <View, String> = Pair(it.mImageViewNewsPhoto, getString(R.string.transition_news_photo))

                val transitionOptions: ActivityOptionsCompat
                        = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this, newsPhotoPair)
                startActivity(intent, transitionOptions.toBundle())
            }
            return
        }
        startActivity(intent)
    }

    override fun showNewsDetails(newsDetail: NewsModel) {
        if (DeviceUtils.hasLollipop()) {
            startPostponedEnterTransition()
        }

        mSelectedNewsItem = newsDetail
        invalidateFavoriteIndicator(newsDetail.isFavorite)

        if (newsDetail.image.isNotEmpty()) {
            val imageViewGallery: ImageView = findViewById(R.id.image_view_gallery) as ImageView
            Picasso
                    .with(CarmenApplication.getContext())
                    .load(newsDetail.image)
                    .placeholder(R.drawable.ic_default_photo)
                    .into(imageViewGallery)
        }

        showMainNewsInformation(newsDetail)
    }

    override fun invalidateFavoriteIndicator(isFavorite: Boolean) {
        val drawableId: Int = if (isFavorite) {
            R.drawable.ic_company_favorite_enable
        } else {
            R.drawable.ic_company_favorite_disable
        }
        mOptionsMenu?.getItem(0)?.icon = ContextCompat.getDrawable(getContext(), drawableId)
    }

    override fun showPopularNews() {
        prepareFragment(R.id.popular_news_fragment_container, PopularNewsHorizontalFragment.newInstance())
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_button)

        invalidateOptionsMenu()
    }

    override fun updateDependencies() {
        super.updateDependencies()
        mSelectedNewsItem = intent.getSerializableExtra(NewsDetailsActivity.NEWS_ARGUMENT) as NewsModel
    }

    private fun userHaveAccess(): Boolean {
        return if (mPresenter.isUserSignedIn()) {
            true
        } else {
            showError(R.string.access_denied_message) {
                // confirm dialog action
                startSignInActivityForResult()
            }
            false
        }
    }

    private fun startSignInActivityForResult() {
        val intent = Intent(getContext(), SignInActivity::class.java)
        startActivityForResult(intent, SignInActivity.SIGN_IN_REQUEST_CODE)
    }

    private fun showMainNewsInformation(newsDetail: NewsModel) {
        (findViewById(R.id.text_view_news_name) as TextView).text = newsDetail.title
        (findViewById(R.id.text_view_news_date) as TextView).text =
                newsDetail.publicationDate.substring(0, newsDetail.publicationDate.indexOf(' '))
        (findViewById(R.id.text_view_news_source) as TextView).text = newsDetail.source
        (findViewById(R.id.text_view_news_text) as TextView).text = newsDetail.text
//        (findViewById(R.id.text_view_news_views_count) as TextView)
    }

}