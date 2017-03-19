package net.ginteam.carmen.kotlin.provider.realm.repository

import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults

/**
 * Created by eugene_shcherbinock on 3/17/17.
 */

interface Repository <T : RealmObject> {

    fun create(model: T)
    fun update(model: T)
    fun delete(model: T)

    fun get(modelId: Long): T
    fun getAll(async: Boolean = true): RealmResults <T>

}

abstract class BaseRepository <T : RealmObject> : Repository <T> {

    override fun create(model: T) = createOrUpdate(model)

    override fun update(model: T) = createOrUpdate(model)

    override fun delete(model: T) {
        getRealmInstance().executeTransaction { model.deleteFromRealm() }
    }

    abstract override fun getAll(async: Boolean): RealmResults<T>
    abstract override fun get(modelId: Long): T

    protected fun getRealmInstance(): Realm = Realm.getDefaultInstance()

    protected fun createOrUpdate(model: T) {
        getRealmInstance().executeTransaction { it.copyToRealmOrUpdate(model) }
    }
}