package com.example.firstkmmapp.data.repository

import com.example.firstkmmapp.data.local.CharacterLocalDataSource
import com.example.firstkmmapp.data.remote.CharacterRemoteDataSource
import com.example.firstkmmapp.domain.model.Character
import com.example.firstkmmapp.domain.repository.CharacterRepository
import com.example.firstkmmapp.domain.util.Paginator
import com.example.firstkmmapp.framework.realm.`object`.CharacterRealm
import com.example.firstkmmapp.pagingBase.DefaultPaginator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CharacterRepositoryImpl(
    private val characterLocalDataSource: CharacterLocalDataSource,
    private val characterRemoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

        override fun getCharacters(scope: CoroutineScope): Paginator<Character, Int> =
        DefaultPaginator(
            scope = scope,
            initialPageIndex = 1,
            query = {
                characterLocalDataSource.getCharactersFlow().map { it.map { item -> item.toCharacter() } }
            },
            fetch = { page, _, _ ->
                characterRemoteDataSource.getCharacters(page!!).map { itemsPage ->
                    itemsPage.map { it.toCharacter() }
                }
            },
            saveFetchResult = { response, reset ->
                characterLocalDataSource.withTransaction {
                    scope.launch {
                        if (reset) {
                            characterLocalDataSource.setCharacters(response.map { CharacterRealm.getRealmObject(it) })
                        } else {
                            characterLocalDataSource.insertCharacters(response.map { CharacterRealm.getRealmObject(it) })
                        }
                    }
                }
            }
        )
}