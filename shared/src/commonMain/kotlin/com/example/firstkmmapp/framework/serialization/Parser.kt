package com.example.firstkmmapp.framework.serialization

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object Parser {

    val json = Json { ignoreUnknownKeys = true }

    inline fun <reified T> getModelFromStringJson(jsonText: String): T =
        json.decodeFromString(jsonText)
}