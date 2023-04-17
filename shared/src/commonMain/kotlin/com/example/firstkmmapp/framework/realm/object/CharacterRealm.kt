package com.example.firstkmmapp.framework.realm.`object`

import com.example.firstkmmapp.domain.model.Character
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class CharacterRealm : RealmObject {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var status: String = ""
    var gender: String = ""
    var image: String = ""
    var created: String = ""

    fun toCharacter(): Character =
        Character(
            id = id,
            name = name,
            status = status,
            gender = gender,
            image = image,
            created = created
        )

    companion object{
        fun getRealmObject(item: Character) =
            CharacterRealm().apply {
                id = item.id
                name = item.name
                status = item.status
                gender = item.gender
                image = item.image
                created = item.created
            }
    }
}