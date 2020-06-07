package com.example.gurunavimenu

import io.realm.DynamicRealm
import io.realm.RealmMigration

class Migration : RealmMigration {

    override  fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val realmSchema = realm.schema
        var oldVersion = oldVersion

        if (oldVersion == 0L) {
            val userSchema = realmSchema.get("Task")
            oldVersion++
        }
    }
}
