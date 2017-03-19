package net.ginteam.carmen.kotlin.provider.realm.repository

import io.realm.RealmResults
import net.ginteam.carmen.kotlin.Constants
import net.ginteam.carmen.kotlin.model.realm.AttributesHistoryModel

/**
 * Created by eugene_shcherbinock on 3/17/17.
 */

class AttributesHistoryRepository : BaseRepository <AttributesHistoryModel>() {

    override fun get(modelId: Long): AttributesHistoryModel
            = getRealmInstance()
            .where(AttributesHistoryModel::class.java)
            .equalTo(Constants.Realm.AttributesHistory.ID, modelId)
            .findFirst()

    override fun getAll(async: Boolean): RealmResults<AttributesHistoryModel>
            = getRealmInstance()
            .where(AttributesHistoryModel::class.java)
            .findAll()
}