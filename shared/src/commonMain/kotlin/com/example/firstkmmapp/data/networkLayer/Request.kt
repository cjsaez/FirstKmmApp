package com.example.firstkmmapp.data.networkLayer

data class Request(
    val requestMethod: RequestMethod,
    val path: String,
    val params: HashMap<String, Any>,
    val headers: HashMap<String, String>
) {
    fun getUrl(baseUrl: String): String {
        var result = baseUrl + path
        if (params.isNotEmpty()) {
            result += params.map { (name, value) -> "$name=$value" }
                .joinToString(prefix = "?", separator = "&")
        }
        return result
    }
}