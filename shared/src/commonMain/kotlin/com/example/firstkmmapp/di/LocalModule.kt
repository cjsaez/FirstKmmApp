package com.example.firstkmmapp.di

import com.example.firstkmmapp.data.local.CharacterLocalDataSource
import com.example.firstkmmapp.framework.realm.CharacterLocalDataSourceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localModule = module {
    singleOf(::CharacterLocalDataSourceImpl) { bind<CharacterLocalDataSource>() }
}