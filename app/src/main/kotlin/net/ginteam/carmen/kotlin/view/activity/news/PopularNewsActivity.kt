package net.ginteam.carmen.kotlin.view.activity.news

import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.MenuItem
import android.view.View
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.common.notifications.FirebaseNotificationsReceiveService
import net.ginteam.carmen.kotlin.contract.PopularNewsActivityContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.model.NotificationModel
import net.ginteam.carmen.kotlin.prepareFragment
import net.ginteam.carmen.kotlin.presenter.news.PopularNewsActivityPresenter
import net.ginteam.carmen.kotlin.view.activity.BaseActivity
import net.ginteam.carmen.kotlin.view.fragment.news.BaseNewsFragment
import net.ginteam.carmen.kotlin.view.fragment.news.PopularNewsFragment
import net.ginteam.carmen.utils.DeviceUtils

/**
 * Created by eugene_shcherbinock on 3/14/17.
 */

class PopularNewsActivity : BaseActivity <PopularNewsActivityContract.View, PopularNewsActivityContract.Presenter>(),
        PopularNewsActivityContract.View, BaseNewsFragment.OnNewsItemSelectedListener {

    override var mPresenter: PopularNewsActivityContract.Presenter = PopularNewsActivityPresenter()

    private lateinit var mIntentNotification: NotificationModel

    override fun getLayoutResId(): Int = R.layout.activity_popular_news

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onNewsItemSelected(newsItem: NewsModel) {
        val intent = Intent(getContext(), NewsDetailsActivity::class.java)
        intent.putExtra(NewsDetailsActivity.NEWS_ARGUMENT, newsItem)

        if (DeviceUtils.hasLollipop()) {
            newsItem.transitionViewHolder?.let {
                val newsPhotoPair: Pair<View, String> = Pair(it.mImageViewNewsPhoto, getString(R.string.transition_news_photo))

                val transitionOptions: ActivityOptionsCompat
                        = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this, newsPhotoPair)
                startActivity(intent, transitionOptions.toBundle())
            }
            return
        }
        startActivity(intent)
    }

    override fun updateDependencies() {
        super.updateDependencies()

        mIntentNotification = intent
                .getSerializableExtra(FirebaseNotificationsReceiveService.NOTIFICATION_ARGUMENT) as NotificationModel
    }

    override fun updateViewDependencies() {
        super.updateViewDependencies()

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_button)

        val days: Int = mIntentNotification.days.toInt()
        setToolbarTitle(getString(R.string.news_activity_popular_title))
        setToolbarSubtitle(resources.getQuantityString(R.plurals.news_activity_popular_subtitle, days, days))

        prepareFragment(R.id.main_fragment_container, PopularNewsFragment.newInstance(days))
    }
}