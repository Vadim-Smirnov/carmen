package net.ginteam.carmen.kotlin.presenter.news

import net.ginteam.carmen.kotlin.api.response.MetaSubscriber
import net.ginteam.carmen.kotlin.contract.RecentNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.model.PaginationModel

/**
 * Created by vadik on 09.03.17.
 */
class RecentNewsPresenter : BaseNewsPresenter<RecentNewsContract.View>(), RecentNewsContract.Presenter  {

    private var isFirsLoading: Boolean = true

    override fun fetchRecentNews(pageNumber: Int) {
        isFirsLoading = pageNumber == 1
        if (isFirsLoading) {
            mView?.showLoading(true)
        }

        mNewsDataSourceProvider
                .fetchCompaniesCountWithParameters(pageNumber)
                .subscribe(object : MetaSubscriber<MutableList<NewsModel>>() {
                    override fun success(model: MutableList<NewsModel>, pagination: PaginationModel) {
                        mView?.showLoading(false)
                        if (isFirsLoading) {
                            mView?.showNews(model, pagination)
                            return
                        }
                        mView?.showNews(model)
                    }

                    override fun error(message: String, isNetworkError: Boolean) {
                        mView?.showError(message, isNetworkError)
                    }
                })
    }
}