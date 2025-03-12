package pl.dawidfendler.domain.repository

import pl.dawidfendler.domain.util.AuthenticationError
import pl.dawidfendler.util.flow.DataResult

interface AuthenticationRepository {
    suspend fun login(email: String, password: String): DataResult<Unit?, AuthenticationError>
    suspend fun googleLogin(idToken: String): DataResult<Unit?, AuthenticationError>
    suspend fun registerUser(email: String, password: String): DataResult<Unit?, AuthenticationError>
    suspend fun logout(): DataResult<Unit, AuthenticationError>
}
