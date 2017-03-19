package net.ginteam.carmen.kotlin.provider.realm.repository

import io.realm.RealmResults
import net.ginteam.carmen.kotlin.Constants
import net.ginteam.carmen.kotlin.model.realm.CostTypeModel

/**
 * Created by eugene_shcherbinock on 3/17/17.
 */

class CostTypeRepository : BaseRepository <CostTypeModel>() {

    override fun get(modelId: Long): CostTypeModel
            = getRealmInstance()
            .where(CostTypeModel::class.java)
            .equalTo(Constants.Realm.CostType.ID, modelId)
            .findFirst()

    override fun getAll(async: Boolean): RealmResults<CostTypeModel>
            = getRealmInstance()
            .where(CostTypeModel::class.java)
            .findAll()
}