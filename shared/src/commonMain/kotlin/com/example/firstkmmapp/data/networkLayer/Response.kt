package com.example.firstkmmapp.data.networkLayer

data class Response<T>(val statusCode: Int, val body: T)
