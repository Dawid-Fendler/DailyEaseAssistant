package pl.dawidfendler.domain.util

import pl.dawidfendler.util.network.NetworkError
import pl.dawidfendler.util.network.NetworkError.REQUEST_TIMEOUT
import pl.dawidfendler.util.network.NetworkError.TOO_MANY_REQUESTS
import pl.dawidfendler.util.network.NetworkError.NO_INTERNET
import pl.dawidfendler.util.network.NetworkError.SERVER_ERROR
import pl.dawidfendler.util.network.NetworkError.UNKNOWN
import pl.dawidfendler.domain.util.AuthenticationError.GetUserFirebaseException
import pl.dawidfendler.domain.util.AuthenticationError.RegistrationFirebaseException
import pl.dawidfendler.domain.util.AuthenticationError.LoginFirebaseException
import pl.dawidfendler.domain.util.AuthenticationError.LogoutFirebaseException
import pl.dawidfendler.domain.util.AuthenticationError.GoogleLoginFirebaseException

fun NetworkError.mapToCustomException() =
    when (this) {
        REQUEST_TIMEOUT -> RequestTimeException()
        TOO_MANY_REQUESTS -> TooManyRequestException()
        NO_INTERNET -> NoInternetException()
        SERVER_ERROR -> ServerException()
        UNKNOWN -> UnknownException()
    }

fun AuthenticationError.mapToCustomException() =
    when (this) {
        LoginFirebaseException -> LoginFirebaseException()
        GoogleLoginFirebaseException -> GoogleLoginFirebaseException()
        RegistrationFirebaseException -> RegistrationFirebaseException()
        LogoutFirebaseException -> LogoutFirebaseException()
        GetUserFirebaseException -> GetUserFirebaseException()
    }