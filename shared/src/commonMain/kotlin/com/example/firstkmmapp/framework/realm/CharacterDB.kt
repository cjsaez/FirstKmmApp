package com.example.firstkmmapp.framework.realm

import com.example.firstkmmapp.framework.realm.`object`.CharacterRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object CharacterDB {

    private val config = RealmConfiguration.Builder(setOf(CharacterRealm::class))
        .name("characters_db")
        .deleteRealmIfMigrationNeeded()
        .schemaVersion(1)
        .build()
    val db = Realm.open(config)
}