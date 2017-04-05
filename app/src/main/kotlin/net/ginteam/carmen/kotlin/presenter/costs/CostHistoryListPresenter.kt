package net.ginteam.carmen.kotlin.presenter.costs

import io.realm.Realm
import net.ginteam.carmen.kotlin.contract.CostHistoryListContract
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter

/**
 * Created by vadimsmirnov on 24.03.17.
 */

class CostHistoryListPresenter: BasePresenter<CostHistoryListContract.View>(), CostHistoryListContract.Presenter {

    override fun fetchHistoryList() {
        val realm: Realm = Realm.getDefaultInstance()
        realm.where(HistoryModel::class.java).findAll().asObservable().subscribe {
            mView?.showCostHistoryList(it)
        }
        realm.close()
    }
}