package com.example.firstkmmapp.framework.realm

import com.example.firstkmmapp.data.local.CharacterLocalDataSource
import com.example.firstkmmapp.framework.realm.`object`.CharacterRealm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CharacterLocalDataSourceImpl : CharacterLocalDataSource {

    override suspend fun insertCharacters(characters: List<CharacterRealm>) {
        withContext(Dispatchers.Default) {
            CharacterDB.db.write {
                characters.forEach { copyToRealm(it, UpdatePolicy.ALL) }
            }
        }
    }

    override suspend fun setCharacters(characters: List<CharacterRealm>) {
        withContext(Dispatchers.Default) {
            CharacterDB.db.write {
                val items = query<CharacterRealm>().find()
                delete(items)
                characters.forEach { copyToRealm(it, UpdatePolicy.ALL) }
            }
        }
    }

    override suspend fun getCharacters(): List<CharacterRealm> =
        CharacterDB.db.query<CharacterRealm>().find()

    override fun getCharactersFlow(): Flow<List<CharacterRealm>> =
        CharacterDB.db.query<CharacterRealm>().asFlow().map { it.list }

    override suspend fun clearCharacterEntityEntities() {
        withContext(Dispatchers.Default) {
            CharacterDB.db.write {
                val items = query<CharacterRealm>().find()
                delete(items)
            }
        }
    }

    override suspend fun <R> withTransaction(block: () -> R): R =
        CharacterDB.db.write {
            block.invoke()
        }
}