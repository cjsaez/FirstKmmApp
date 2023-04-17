package com.example.firstkmmapp.data.networkLayer

/**
 * This method in correspondence of whether the response is satisfactory or not mapping a Response to a desired model
 * or choose the corresponding error, in both cases encapsulate the result into a kotlin.Result.
 *
 * @param T the type of the desired object.
 *
 * @param[response] is the response that it want to be processed.
 * @param[typeOfT] The specific genericized type of [T]. You can obtain this type by using the
 * com.example.firstkmmapp.data.networkLayer.TypeReference class. For example, to get the type for
 * Collection<Foo>, you should use:
 *
 * val typeOfT: Type = TypeReference<Collection<Foo>>(){}.type;
 *
 * @return a kotlin.Result of the specified model with the effected mapping or the selected error.
 */
suspend fun <T> tryMap(
    response: Response,
    parse: (String) -> T
): Result<T> {

    //TODO("Handle status code properly here https://github.com/Leearning/pelis/issues/11")
    return map(response.body, parse)
}

/**
 * This method mapping a Response to a desired model and encapsulate it into a kotlin.Result.
 *
 * @param T the type of the desired object.
 *
 * @param[body] is the string that it want to be mapped.
 * @param[typeOfT] The specific genericized type of [T]. You can obtain this type by using the
 * com.example.firstkmmapp.data.networkLayer.TypeReference class. For example, to get the type for
 * Collection<Foo>, you should use:
 *
 * val typeOfT: Type = TypeReference<Collection<Foo>>(){}.type;
 *
 * @return a kotlin.Result of specified model that was obtained parsing the {response.body} to [typeOfT].
 * In case that [body] can't be mapper to [typeOfT] a failure Result will be returned.
 */
private suspend fun <T> map(
    body: String,
    parse: (String) -> T
): Result<T> =
    Result.runCatching { parse(body) }