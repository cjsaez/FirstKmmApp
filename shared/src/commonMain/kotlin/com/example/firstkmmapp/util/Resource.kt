package com.example.firstkmmapp.util

sealed class Resource<T, K>(val data: T? = null, val message: Throwable? = null, val page: K?) {
    class Loading<T, K>(data: T? = null, page: K?) : Resource<T, K>(data = data, page = page)
    class Success<T, K>(data: T?, page: K?) : Resource<T, K>(data = data, page = page)
    class Error<T, K>(message: Throwable, data: T? = null, page: K?) : Resource<T, K>(data, message, page)
}