package com.example.gurunavimenu

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.where

open class FavoriteStore :RealmObject() {
    @PrimaryKey
    var primaryId : Long? = 0L
    var id: String? = ""
    var title: String? = ""
    var image: String? = ""
    var category: String? = ""
    var area: String? = ""

    //useはrealmの拡張関数で、自動的にcloseしてくれる便利機能
    companion object {
        fun findAll(): List<FavoriteStore> =
            Realm.getDefaultInstance().use { realm ->
                realm.where(FavoriteStore::class.java)
                    .sort(FavoriteStore::id.name)
                    .findAll()
                    .let { realm.copyFromRealm(it) }
            }

        fun findBy(id: String): FavoriteStore? =
            Realm.getDefaultInstance().use { realm ->
                realm.where(FavoriteStore::class.java)
                    .equalTo(FavoriteStore::id.name, id)
                    .findFirst()?.also {
                        realm.copyFromRealm(it)
                    }
            }
    }
}




