package pl.dawidfendler.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun login(email: String, password: String): Flow<FirebaseUser?>
    fun googleLogin(idToken: String): Flow<FirebaseUser?>
    fun registerUser(email: String, password: String): Flow<FirebaseUser?>
    fun logout(): Flow<Unit>
    fun getUser(): Flow<FirebaseUser?>
}
