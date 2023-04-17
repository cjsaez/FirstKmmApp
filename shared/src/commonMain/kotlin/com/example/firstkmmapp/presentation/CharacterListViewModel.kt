package com.example.firstkmmapp.presentation

import com.example.firstkmmapp.domain.model.Character
import com.example.firstkmmapp.domain.use_case.GetCharacters
import com.example.firstkmmapp.pagingBase.PagingListViewModel
import kotlinx.coroutines.CoroutineScope

class CharacterListViewModel(
    coroutineScope: CoroutineScope? = null,
    getCharactersUseCase: GetCharacters
) : PagingListViewModel<Character, Int>(
    coroutineScope = coroutineScope,
    getItemsUseCase = getCharactersUseCase
)