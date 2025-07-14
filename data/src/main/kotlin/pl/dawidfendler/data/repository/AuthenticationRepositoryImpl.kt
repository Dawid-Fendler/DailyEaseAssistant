package pl.dawidfendler.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.domain.util.AuthenticationError
import pl.dawidfendler.util.ext.awaitCustom
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.logger.Logger
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val logger: Logger
) : AuthenticationRepository {

    override suspend fun login(
        email: String,
        password: String
    ): DataResult<Unit?, AuthenticationError> {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).awaitCustom()
            return DataResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(TAG, "Error during login: ${e.message}")
            return DataResult.Error(AuthenticationError.LoginFirebaseException)
        }
    }

    override suspend fun googleLogin(idToken: String): DataResult<Unit?, AuthenticationError> {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential).awaitCustom()
            return DataResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(TAG, "Error during Google login: ${e.message}")
            return DataResult.Error(AuthenticationError.GoogleLoginFirebaseException)
        }
    }

    override suspend fun registerUser(
        email: String,
        password: String
    ): DataResult<Unit, AuthenticationError> {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).awaitCustom()
            return DataResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(TAG, "Error during registration: ${e.message}")
            return DataResult.Error(AuthenticationError.RegistrationFirebaseException)
        }
    }

    override suspend fun logout(): DataResult<Unit, AuthenticationError> {
        try {
            firebaseAuth.signOut()
            return DataResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(TAG, "Error during logout: ${e.message}")
            return DataResult.Error(AuthenticationError.LogoutFirebaseException)
        }
    }

    companion object {
        private const val TAG = "AuthenticationRepositoryImpl"
    }
}
