package net.ginteam.carmen.kotlin.presenter.costs

import io.realm.Realm
import net.ginteam.carmen.kotlin.Constants
import net.ginteam.carmen.kotlin.contract.CostDetailsActivityContract
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel
import net.ginteam.carmen.kotlin.model.realm.HistoryModel
import net.ginteam.carmen.kotlin.presenter.BasePresenter

/**
 * Created by eugene_shcherbinock on 3/20/17.
 */

open class CostDetailsActivityPresenter <V: CostDetailsActivityContract.View> : BasePresenter <V>(),
        CostDetailsActivityContract.Presenter <V> {

    override fun fetchHistoryById(id: Long) {
        val realmInstance: Realm = Realm.getDefaultInstance()
        realmInstance
                .where(AttributesHistoryModel::class.java)
                .equalTo("${Constants.Realm.AttributesHistory.HISTORY}.${Constants.Realm.History.ID}", id)
                .findAll()
                .asObservable()
                .subscribe {
                    val history: HistoryModel = it.first().history!!
                    mView?.setCostInformation(history.costType!!)
                    mView?.setHistoryInformation(history, it)
                }
    }

    override fun fetchCostById(id: Long) {
        val realmInstance: Realm = Realm.getDefaultInstance()
        realmInstance
                .where(CostTypeModel::class.java)
                .equalTo(Constants.Realm.CostType.ID, id)
                .findFirst()
                .asObservable <CostTypeModel>()
                .subscribe {
                    mView?.setCostInformation(it)
                    realmInstance.close()
                }
    }
}