package com.example.firstkmmapp.data.networkLayer

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

open class NetworkResponseValidator {
    open suspend fun validateHttpResponse(response: HttpResponse){
        if (response.status.value !in 200..299) {
            val error: Error = response.body()
            throw Exception("Code: ${response.status.value}, message: ${error.message}")
        }
    }
}