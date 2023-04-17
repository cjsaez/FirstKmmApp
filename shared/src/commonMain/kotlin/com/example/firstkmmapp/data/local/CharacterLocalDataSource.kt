package com.example.firstkmmapp.data.local

import com.example.firstkmmapp.framework.realm.`object`.CharacterRealm
import kotlinx.coroutines.flow.Flow

interface CharacterLocalDataSource {

    suspend fun insertCharacters(characters: List<CharacterRealm>)
    suspend fun getCharacters(): List<CharacterRealm>
    fun getCharactersFlow(): Flow<List<CharacterRealm>>
    suspend fun clearCharacterEntityEntities()
    suspend fun <R> withTransaction(block: () -> R): R
    suspend fun setCharacters(characters: List<CharacterRealm>)
}