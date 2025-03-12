package pl.dawidfendler.domain.util

class RequestTimeException : Exception()
class TooManyRequestException : Exception()
class NoInternetException : Exception()
class ServerException : Exception()
class UnknownException : Exception()
class LoginFirebaseException : Exception("Get error during login")
class GoogleLoginFirebaseException : Exception("Get error during google login")
class RegistrationFirebaseException : Exception("Get error during registration")
class LogoutFirebaseException : Exception("Get error during logout")
