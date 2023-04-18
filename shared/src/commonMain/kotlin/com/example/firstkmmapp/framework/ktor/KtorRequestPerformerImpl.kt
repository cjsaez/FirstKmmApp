package com.example.firstkmmapp.framework.ktor

import com.example.firstkmmapp.data.networkLayer.NetworkEngineRequestPerformer
import com.example.firstkmmapp.data.networkLayer.Response
import com.example.firstkmmapp.data.networkLayer.Request
import com.example.firstkmmapp.data.networkLayer.RequestMethod
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.platformType
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class KtorRequestPerformerImpl(
    private val httpClient: HttpClient
) : NetworkEngineRequestPerformer {

    private suspend fun runEngineRequest(request: UrlRequest): HttpResponse =
        httpClient.request(request.url, request.block)

    override suspend fun <T> performRequest(baseUrl: String, request: Request, typeInfo: TypeInfo): Response<T> {
        val response = runEngineRequest(UrlRequest.from(baseUrl, request))
        return Response(response.status.value, response.body(typeInfo))
    }

    private fun Headers.Companion.from(request: Request): HeadersBuilder {
        val headersBuilder = HeadersBuilder()
        request.headers.forEach { headersBuilder.append(it.key, it.value) }
        return headersBuilder
    }

    private fun HttpMethod.Companion.from(request: Request): HttpMethod =
        when (request.requestMethod) {
            RequestMethod.GET -> Get
            RequestMethod.POST -> Post
            RequestMethod.PUT -> Put
        }

    private fun UrlRequest.Companion.from(baseUrl: String, request: Request): UrlRequest =
        UrlRequest(request.getUrl(baseUrl)) {
            method = HttpMethod.from(request)
            headers.appendAll(Headers.from(request))
        }

    data class UrlRequest(
        val url: String,
        val block: HttpRequestBuilder.() -> Unit
    ) {
        companion object {}
    }
}