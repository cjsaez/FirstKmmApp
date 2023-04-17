package com.example.firstkmmapp.di

import com.example.firstkmmapp.domain.use_case.GetCharacters
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::GetCharacters)
}