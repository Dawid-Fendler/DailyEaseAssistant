package pl.dawidfendler.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.util.exception.GetUserFirebaseException
import pl.dawidfendler.util.exception.GoogleLoginFirebaseException
import pl.dawidfendler.util.exception.LoginFirebaseException
import pl.dawidfendler.util.exception.LogoutFirebaseException
import pl.dawidfendler.util.exception.RegistrationFirebaseException
import pl.dawidfendler.util.ext.awaitCustom
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFireStore: FirebaseFirestore,
    private val dispatcherProvider: DispatcherProvider
) : AuthenticationRepository {

    override fun login(email: String, password: String): Flow<FirebaseUser?> = flow {
        try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).awaitCustom()
            emit(authResult.user)
        } catch (e: Exception) {
            Log.e("login Exception", "$e")
            throw LoginFirebaseException()
        }
    }.flowOn(dispatcherProvider.io)

    override fun googleLogin(idToken: String): Flow<FirebaseUser?> = flow {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).awaitCustom()
            emit(authResult.user)
        } catch (e: Exception) {
            Log.e("googleLogin Exception", "$e")
            throw GoogleLoginFirebaseException()
        }
    }.flowOn(dispatcherProvider.io)

    override fun registerUser(email: String, password: String): Flow<FirebaseUser?> = flow {
        try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).awaitCustom()
            emit(authResult.user)
        } catch (e: Exception) {
            Log.e("registerUser Exception", "$e")
            throw RegistrationFirebaseException()
        }
    }.flowOn(dispatcherProvider.io)

    override fun logout(): Flow<Unit> = flow {
        try {
            firebaseAuth.signOut()
            emit(Unit)
        } catch (e: Exception) {
            Log.e("logout Exception", "$e")
            throw LogoutFirebaseException()
        }
    }.flowOn(dispatcherProvider.io)

    override fun getUser(): Flow<FirebaseUser?> = flow {
        try {
            val currentUser = firebaseAuth.currentUser
            emit(currentUser)
        } catch (e: Exception) {
            Log.e("getUser Exception", "$e")
            throw GetUserFirebaseException()
        }
    }.flowOn(dispatcherProvider.io)
}
