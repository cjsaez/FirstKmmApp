package com.example.firstkmmapp.data.remote

import com.example.firstkmmapp.framework.ktor.dto.CharacterDto
import com.example.firstkmmapp.util.Page

interface CharacterRemoteDataSource {

    suspend fun getCharacters(page: Int): Result<Page<CharacterDto, Int>>
}