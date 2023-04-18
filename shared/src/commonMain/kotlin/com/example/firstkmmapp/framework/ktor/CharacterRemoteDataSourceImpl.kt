package com.example.firstkmmapp.framework.ktor

import com.example.firstkmmapp.data.networkLayer.NetworkEngineRequestPerformer
import com.example.firstkmmapp.data.networkLayer.requestBuilders.GetCharactersRequestBuilder
import com.example.firstkmmapp.data.remote.CharacterRemoteDataSource
import com.example.firstkmmapp.framework.ktor.dto.CharacterDto
import com.example.firstkmmapp.framework.ktor.dto.CharacterPageDataDto
import com.example.firstkmmapp.util.Page
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val CHARACTER_API_BASE_URL = "https://rickandmortyapi.com"

class CharacterRemoteDataSourceImpl(
    private val engine: NetworkEngineRequestPerformer
    ) : CharacterRemoteDataSource {

    override suspend fun getCharacters(page: Int): Result<Page<CharacterDto, Int>> =
        withContext(Dispatchers.Default){
            val request = GetCharactersRequestBuilder(page).build()
            val response = engine.performRequest<CharacterPageDataDto>(
                baseUrl = CHARACTER_API_BASE_URL,
                request = request,
                typeInfo = typeInfo<CharacterPageDataDto>()
            )
//            tryMap<CharacterPageDataDto>(response) {
//                Parser.getModelFromStringJson(it)
//            }.mapCatching { it.getPage(page) }
            Result.runCatching { response.body }.mapCatching { it.getPage(page) }
        }
}