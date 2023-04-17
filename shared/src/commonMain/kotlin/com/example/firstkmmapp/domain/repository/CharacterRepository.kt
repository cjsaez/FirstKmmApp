package com.example.firstkmmapp.domain.repository

import com.example.firstkmmapp.domain.model.Character
import com.example.firstkmmapp.domain.util.Paginator
import kotlinx.coroutines.CoroutineScope

interface CharacterRepository {

    fun getCharacters(scope: CoroutineScope): Paginator<Character, Int>
}