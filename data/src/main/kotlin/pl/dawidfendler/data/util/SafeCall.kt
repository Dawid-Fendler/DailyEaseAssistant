package pl.dawidfendler.data.util

import android.util.Log
import kotlinx.coroutines.ensureActive
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.network.NetworkError
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

internal suspend inline fun <reified T> safeCall(
    responseName: String,
    execute: () -> Response<T>
): DataResult<T?, NetworkError> {
    val response = try {
        execute()
    } catch(e: UnresolvedAddressException) {
        Log.e(responseName, "$e")
        return DataResult.Error(NetworkError.NO_INTERNET)
    } catch(e: Exception) {
        coroutineContext.ensureActive()
        Log.e(responseName, "$e")
        return DataResult.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(responseName, response)
}