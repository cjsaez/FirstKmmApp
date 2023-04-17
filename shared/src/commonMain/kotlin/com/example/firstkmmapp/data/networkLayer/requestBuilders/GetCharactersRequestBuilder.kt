package com.example.firstkmmapp.data.networkLayer.requestBuilders

import com.example.firstkmmapp.data.networkLayer.Request
import com.example.firstkmmapp.data.networkLayer.RequestMethod

private const val CHARACTERS_ENDPOINT = "/api/character"

class GetCharactersRequestBuilder(private val page: Int): RequestBuilder {

    override suspend fun build(): Request {
        val params = HashMap<String, Any>()
        params["page"] = page
        return Request(
            requestMethod = RequestMethod.GET,
            path = CHARACTERS_ENDPOINT,
            headers = HashMap(),
            params = params
        )
    }
}