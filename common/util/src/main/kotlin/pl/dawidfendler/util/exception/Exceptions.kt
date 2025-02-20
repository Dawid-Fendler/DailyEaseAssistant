package pl.dawidfendler.util.exception

class LoginFirebaseException : Exception("Get error during login")
class GoogleLoginFirebaseException : Exception("Get error during google login")
class RegistrationFirebaseException : Exception("Get error during registration")
class LogoutFirebaseException : Exception("Get error during logout")
class GetUserFirebaseException : Exception("Get error during get user")
class MaxAccountBalanceException: Exception("You have exceeded your account limit")
class MinAccountBalanceException: Exception("You have exceeded your account debt limit")
