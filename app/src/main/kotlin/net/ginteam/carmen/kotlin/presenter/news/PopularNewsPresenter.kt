package net.ginteam.carmen.kotlin.presenter.news

import net.ginteam.carmen.kotlin.api.response.MetaSubscriber
import net.ginteam.carmen.kotlin.contract.PopularNewsContract
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.model.PaginationModel

/**
 * Created by vadik on 09.03.17.
 */
class PopularNewsPresenter : BaseNewsPresenter<PopularNewsContract.View>(), PopularNewsContract.Presenter {

    private var isFirsLoading: Boolean = true

    override fun fetchPopularNews(forDays: Int, pageNumber: Int) {
        isFirsLoading = pageNumber == 1
        if (isFirsLoading) {
            mView?.showLoading(true)
        }

        mNewsDataSourceProvider
                .fetchPopularNews(forDays, pageNumber)
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