package com.example.firstkmmapp.di

import com.example.firstkmmapp.data.networkLayer.NetworkEngineRequestPerformer
import com.example.firstkmmapp.data.networkLayer.NetworkResponseValidator
import com.example.firstkmmapp.data.remote.CharacterRemoteDataSource
import com.example.firstkmmapp.framework.ktor.KtorRequestPerformerImpl
import com.example.firstkmmapp.framework.ktor.CharacterRemoteDataSourceImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import mu.KLogger
import mu.KotlinLogging
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single { KotlinLogging.logger {} }
    singleOf(::NetworkResponseValidator)
    single { HttpClient(CIO) {
        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    get<KLogger>().info { "HTTP Client $message" }
                }
            }
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
        HttpResponseValidator {
            validateResponse { response ->
                get<NetworkResponseValidator>().validateHttpResponse(response)
            }
        }
    } }
    singleOf(::KtorRequestPerformerImpl) { bind<NetworkEngineRequestPerformer>() }
    singleOf(::CharacterRemoteDataSourceImpl) { bind<CharacterRemoteDataSource>() }
}