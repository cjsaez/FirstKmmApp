package com.example.firstkmmapp.framework.ktor.dto

import com.example.firstkmmapp.domain.model.Character
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: Long,
    var name: String,
    val status: String,
    val gender: String,
    val image: String,
    val created: String
) {
    fun toCharacter(): Character =
        Character(
            id = id,
            name = name,
            status = status,
            gender = gender,
            image = image,
            created = created
        )
}