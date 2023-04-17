package com.example.firstkmmapp.di

import com.example.firstkmmapp.data.networkLayer.NetworkEngineRequestPerformer
import com.example.firstkmmapp.data.remote.CharacterRemoteDataSource
import com.example.firstkmmapp.framework.ktor.KtorRequestPerformerImpl
import com.example.firstkmmapp.framework.ktor.CharacterRemoteDataSourceImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single { HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    } }
    singleOf(::KtorRequestPerformerImpl) { bind<NetworkEngineRequestPerformer>() }
    singleOf(::CharacterRemoteDataSourceImpl) { bind<CharacterRemoteDataSource>() }
}