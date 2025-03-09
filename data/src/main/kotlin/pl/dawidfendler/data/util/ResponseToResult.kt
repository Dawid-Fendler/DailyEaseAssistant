package pl.dawidfendler.data.util

import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.network.NetworkError
import retrofit2.Response

internal inline fun <reified T> responseToResult(
    responseName: String,
    response: Response<T>
): DataResult<T?, NetworkError> {
    return when (response.code()) {
        in 200..299 -> {
            DataResult.Success(response.body())
        }

        408 -> {
            //  TODO ADD OWN LOGGER
            DataResult.Error(NetworkError.REQUEST_TIMEOUT)
        }

        429 -> {
            //  TODO ADD OWN LOGGER
            DataResult.Error(NetworkError.TOO_MANY_REQUESTS)
        }

        in 500..599 -> {
            //  TODO ADD OWN LOGGER
            DataResult.Error(NetworkError.SERVER_ERROR)
        }

        else -> {
            //  TODO ADD OWN LOGGER
            DataResult.Error(NetworkError.UNKNOWN)
        }
    }
}
