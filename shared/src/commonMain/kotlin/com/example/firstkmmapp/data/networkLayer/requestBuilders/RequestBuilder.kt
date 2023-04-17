package com.example.firstkmmapp.data.networkLayer.requestBuilders

import com.example.firstkmmapp.data.networkLayer.Request

interface RequestBuilder {
    suspend fun build(): Request
}