package pl.dawidfendler.data.util

import kotlinx.coroutines.ensureActive
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.logger.Logger
import pl.dawidfendler.util.network.NetworkError
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

internal suspend inline fun <reified T> safeCall(
    responseName: String,
    logger: Logger,
    execute: () -> Response<T>
): DataResult<T?, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        logger.e(TAG, "Unresolved address exception in $responseName: ${e.message}")
        return DataResult.Error(NetworkError.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        logger.e(TAG, "Exception in $responseName: ${e.message}")
        return DataResult.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(responseName, response, logger)
}

private const val TAG = "SafeCall"
