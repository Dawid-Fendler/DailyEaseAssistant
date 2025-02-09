package pl.dawidfendler.domain.util

import pl.dawidfendler.util.network.NetworkError
import pl.dawidfendler.util.network.NetworkError.*

fun NetworkError.mapToCustomException() =
    when (this) {
        REQUEST_TIMEOUT -> RequestTimeException()
        TOO_MANY_REQUESTS -> TooManyRequestException()
        NO_INTERNET -> NoInternetException()
        SERVER_ERROR -> ServerException()
        UNKNOWN -> UnknownException()
    }