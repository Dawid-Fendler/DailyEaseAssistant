package pl.dawidfendler.data.util

import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.logger.Logger
import pl.dawidfendler.util.network.NetworkError
import retrofit2.Response

internal inline fun <reified T> responseToResult(
    responseName: String,
    response: Response<T>,
    logger: Logger
): DataResult<T?, NetworkError> {
    return when (response.code()) {
        in 200..299 -> {
            DataResult.Success(response.body())
        }

        408 -> {
            logger.e(TAG, "Request timeout in $responseName: ${response.message()}")
            DataResult.Error(NetworkError.REQUEST_TIMEOUT)
        }

        429 -> {
            logger.e(TAG, "Too many requests in $responseName: ${response.message()}")
            DataResult.Error(NetworkError.TOO_MANY_REQUESTS)
        }

        in 500..599 -> {
            logger.e(TAG, "Server error in $responseName: ${response.message()}")
            DataResult.Error(NetworkError.SERVER_ERROR)
        }

        else -> {
            logger.e(TAG, "Unknown error in $responseName: ${response.message()}")
            DataResult.Error(NetworkError.UNKNOWN)
        }
    }
}

private const val TAG = "ResponseToResult"