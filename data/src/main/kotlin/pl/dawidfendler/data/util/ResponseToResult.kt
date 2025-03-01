package pl.dawidfendler.data.util

import android.util.Log
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
            Log.e(responseName, "${response.errorBody()}")
            DataResult.Error(NetworkError.REQUEST_TIMEOUT)
        }

        429 -> {
            Log.e(responseName, "${response.errorBody()}")
            DataResult.Error(NetworkError.TOO_MANY_REQUESTS)
        }

        in 500..599 -> {
            Log.e(responseName, "${response.errorBody()}")
            DataResult.Error(NetworkError.SERVER_ERROR)
        }

        else -> {
            Log.e(responseName, "${response.errorBody()}")
            DataResult.Error(NetworkError.UNKNOWN)
        }
    }
}
