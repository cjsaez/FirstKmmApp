package com.example.firstkmmapp.di

import com.example.firstkmmapp.domain.repository.CharacterRepository
import com.example.firstkmmapp.data.repository.CharacterRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::CharacterRepositoryImpl) { bind<CharacterRepository>() }
}