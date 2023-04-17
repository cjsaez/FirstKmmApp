package com.example.firstkmmapp.framework.ktor.dto

import com.example.firstkmmapp.framework.ktor.dto.CharacterDto
import com.example.firstkmmapp.framework.ktor.dto.PageInfoDto
import com.example.firstkmmapp.util.Page
import kotlinx.serialization.Serializable

@Serializable
data class CharacterPageDataDto(
    val info: PageInfoDto,
    val results: List<CharacterDto>
) {
    fun getPage(currentPageNumber: Int) = Page(
        items = results,
        hasMore = currentPageNumber < info.pages,
        nextPageIndex = currentPageNumber + 1)
}