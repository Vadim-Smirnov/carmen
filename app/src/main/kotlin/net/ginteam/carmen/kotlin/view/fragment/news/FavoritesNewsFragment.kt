package net.ginteam.carmen.kotlin.view.fragment.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.contract.FavoritesNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.presenter.news.FavoritesNewsPresenter
import net.ginteam.carmen.kotlin.view.adapter.news.FavoritesNewsAdapter

/**
 * Created by vadik on 09.03.17.
 */
class FavoritesNewsFragment : BaseVerticalNewsFragment<FavoritesNewsAdapter,
        FavoritesNewsContract.View, FavoritesNewsContract.Presenter>(),
        FavoritesNewsContract.View {

    override var mPresenter: FavoritesNewsContract.Presenter = FavoritesNewsPresenter()

    companion object {
        const val POSITION: Int = 2
        private const val SNACKBAR_DURATION_SHOW_MILLISECONDS: Int = 5000

        fun newInstance(): FavoritesNewsFragment = FavoritesNewsFragment()

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        fetchNews()
    }

    override fun initializeAdapter(news: MutableList<NewsModel>,
                                   onItemClick: (NewsModel) -> Unit,
                                   onFavoriteClick: (NewsModel, Boolean) -> Unit
    ): FavoritesNewsAdapter = FavoritesNewsAdapter(news, onItemClick, onFavoriteClick)

    override fun showFavoriteConfirmationMessage(newsItem: NewsModel, messageResId: Int) {
        val snackbar: Snackbar = Snackbar.make(mFragmentView, getString(messageResId), Snackbar.LENGTH_LONG)
        // if company had been removed from favorites
        if (!newsItem.isFavorite) {
            // remove from list adapter
            mNewsAdapter.removeNewsItem(newsItem)

            // set snackbar action to restore
            snackbar.setAction(R.string.undo_news_removed_message) {
                // add company to favorites
                mPresenter.addNewsItemToFavorites(newsItem)
            }
            snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.colorApplicationBlue))
            snackbar.duration = SNACKBAR_DURATION_SHOW_MILLISECONDS
        } else {
            // if company restored to favorites insert it in adapter
            mNewsAdapter.restoreRemovedNewsItem()
        }
        snackbar.show()
    }

    override fun fetchNews() {
        mPresenter.fetchFavoritesNews(mCurrentPaginationPage)
    }

}