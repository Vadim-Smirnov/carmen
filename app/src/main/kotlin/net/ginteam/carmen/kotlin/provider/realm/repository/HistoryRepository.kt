package net.ginteam.carmen.kotlin.provider.realm.repository

import io.realm.RealmResults
import net.ginteam.carmen.kotlin.Constants
import net.ginteam.carmen.kotlin.model.realm.HistoryModel

/**
 * Created by eugene_shcherbinock on 3/17/17.
 */

class HistoryRepository : BaseRepository <HistoryModel>() {

    override fun get(modelId: Long): HistoryModel
            = getRealmInstance()
            .where(HistoryModel::class.java)
            .equalTo(Constants.Realm.History.ID, modelId)
            .findFirst()

    override fun getAll(async: Boolean): RealmResults<HistoryModel>
            = getRealmInstance()
            .where(HistoryModel::class.java)
            .findAll()
}