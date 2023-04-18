package com.example.firstkmmapp.data.networkLayer

import io.ktor.util.reflect.TypeInfo
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * An interface to abstract the requesting resources to the network implementation
 * for a selected networking engine.
 */
interface NetworkEngineRequestPerformer {

    /**
     * This method request a resource from network.
     *
     * @param[request] is an instance of a class that contains all
     * information that point at a resource in a backend.
     *
     * @return a Response class instance with info about the status code and body obtained by the engine response.
     */
    suspend fun <T> performRequest(baseUrl: String, request: Request, typeInfo: TypeInfo): Response<T>
}