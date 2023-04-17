package com.example.firstkmmapp.framework.ktor.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageInfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)