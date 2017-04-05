package net.ginteam.carmen.kotlin.contract

import net.ginteam.carmen.kotlin.model.realm.HistoryModel

/**
 * Created by vadimsmirnov on 24.03.17.
 */

object CostHistoryListContract {

    interface View : BaseContract.View {

        fun showCostHistoryList(history: List <HistoryModel>)

    }

    interface Presenter : BaseContract.Presenter <View> {

        fun fetchHistoryList()

    }

}