package com.example.firstkmmapp.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstkmmapp.domain.use_case.GetCharacters
import com.example.firstkmmapp.presentation.CharacterListViewModel

class AndroidCharacterListViewModel(getCharactersUseCase: GetCharacters) : ViewModel() {

    private val viewModel by lazy {
        CharacterListViewModel(
            coroutineScope = viewModelScope,
            getCharactersUseCase = getCharactersUseCase
        )
    }

    val pagingUiState = viewModel.pagingUiState
    val receivePagingListActions = viewModel.receivePagingListActions
}