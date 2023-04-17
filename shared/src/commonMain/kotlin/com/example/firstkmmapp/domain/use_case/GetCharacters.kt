package com.example.firstkmmapp.domain.use_case

import com.example.firstkmmapp.domain.repository.CharacterRepository
import com.example.firstkmmapp.domain.util.Paginator
import com.example.firstkmmapp.domain.model.Character
import com.example.firstkmmapp.pagingBase.PagingListGetItemsUseCase
import kotlinx.coroutines.CoroutineScope

class GetCharacters(
    private val characterRepository: CharacterRepository
) : PagingListGetItemsUseCase<Character, Int> {

    override operator fun invoke(scope: CoroutineScope): Paginator<Character, Int> =
        characterRepository.getCharacters(scope)
}