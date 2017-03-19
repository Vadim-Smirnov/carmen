package net.ginteam.carmen.kotlin.provider.realm.repository

import io.realm.RealmResults
import net.ginteam.carmen.kotlin.Constants
import net.ginteam.carmen.kotlin.model.realm.CostTypeAttributeModel

/**
 * Created by eugene_shcherbinock on 3/17/17.
 */

class CostAttributeRepository : BaseRepository <CostTypeAttributeModel>() {

    override fun get(modelId: Long): CostTypeAttributeModel
            = getRealmInstance()
            .where(CostTypeAttributeModel::class.java)
            .equalTo(Constants.Realm.CostTypeAttribute.ID, modelId)
            .findFirst()

    override fun getAll(async: Boolean): RealmResults<CostTypeAttributeModel>
            = getRealmInstance()
            .where(CostTypeAttributeModel::class.java)
            .findAll()
}