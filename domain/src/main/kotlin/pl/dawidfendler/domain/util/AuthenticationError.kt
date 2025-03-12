package pl.dawidfendler.domain.util

import pl.dawidfendler.util.network.Error

enum class AuthenticationError : Error {
    LoginFirebaseException,
    GoogleLoginFirebaseException,
    RegistrationFirebaseException,
    LogoutFirebaseException
}