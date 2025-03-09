package pl.dawidfendler.data.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.oAuthCredential
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import pl.dawidfendler.domain.util.AuthenticationError
import pl.dawidfendler.util.flow.DataResult

class AuthenticationRepositoryTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authenticationRepository: AuthenticationRepositoryImpl
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var authResult: AuthResult
    private lateinit var task: Task<AuthResult>
    private lateinit var listenerSlot: CapturingSlot<OnCompleteListener<AuthResult>>

    @Before
    fun setUp() {
        firebaseAuth = mockk(relaxed = true)
        firebaseUser = mockk<FirebaseUser>(relaxed = true)
        authResult = mockk<AuthResult> {
            every { user } returns firebaseUser
        }
        task = mockk<Task<AuthResult>>(relaxed = true)
        listenerSlot = slot<OnCompleteListener<AuthResult>>()
        every { task.addOnCompleteListener(capture(listenerSlot)) } answers {
            listenerSlot.captured.onComplete(task)
            task
        }
        authenticationRepository = AuthenticationRepositoryImpl(firebaseAuth)
    }

    @Test
    fun `When login is called , then i should return DataResult Success with Unit`() {
        runTest {
            // GIVEN
            val email = "test@example.pl"
            val password = "password"

            every { task.result } returns authResult
            every { task.exception } returns null

            every { firebaseAuth.signInWithEmailAndPassword(email, password) } returns task

            val result = authenticationRepository.login(email, password)

            assertThat(result).isInstanceOf(DataResult.Success::class.java)

            val successData = (result as DataResult.Success).data
            assertThat(successData).isEqualTo(Unit)
        }
    }

    @Test
    fun `When login is called , then i should return DataResult Error with LoginFirebaseException`() {
        runTest {
            // GIVEN
            val email = "test@example.pl"
            val password = "password"

            every { task.result } returns authResult
            every { task.exception } returns Exception()

            every { firebaseAuth.signInWithEmailAndPassword(email, password) } returns task

            val result = authenticationRepository.login(email, password)

            assertThat(result).isInstanceOf(DataResult.Error::class.java)

            val successData = (result as DataResult.Error).error
            assertThat(successData).isEqualTo(AuthenticationError.LoginFirebaseException)
        }
    }

    @Test
    fun `When registerUser is called , then i should return DataResult Success with Unit`() {
        runTest {
            // GIVEN
            val email = "test@example.pl"
            val password = "password"
            every { task.result } returns authResult
            every { task.exception } returns null

            every { firebaseAuth.createUserWithEmailAndPassword(email, password) } returns task

            val result = authenticationRepository.registerUser(email, password)

            assertThat(result).isInstanceOf(DataResult.Success::class.java)

            val successData = (result as DataResult.Success).data
            assertThat(successData).isEqualTo(Unit)
        }
    }

    @Test
    fun `When registerUser is called , then i should return DataResult Error with RegistrationFirebaseException`() {
        runTest {
            // GIVEN
            val email = "test@example.pl"
            val password = "password"

            every { task.result } returns authResult
            every { task.exception } returns Exception()

            every { firebaseAuth.createUserWithEmailAndPassword(email, password) } returns task

            val result = authenticationRepository.registerUser(email, password)

            assertThat(result).isInstanceOf(DataResult.Error::class.java)

            val successData = (result as DataResult.Error).error
            assertThat(successData).isEqualTo(AuthenticationError.RegistrationFirebaseException)
        }
    }

    @Test
    fun `When getUser is called , then i should return DataResult Success with FirebaseUser`() {
        runTest {
            // GIVEN
            every { firebaseAuth.currentUser } returns firebaseUser

            val result = authenticationRepository.getUser()

            assertThat(result).isInstanceOf(DataResult.Success::class.java)

            val successData = (result as DataResult.Success).data
            assertThat(successData?.uid).isEqualTo(firebaseUser.uid)
            assertThat(successData?.email).isEqualTo(firebaseUser.email)
        }
    }

    @Test
    fun `When getUser is called , then i should return DataResult Error with GetUserFirebaseException`() {
        runTest {
            // GIVEN
            every { firebaseAuth.currentUser } throws Exception()

            val result = authenticationRepository.getUser()

            assertThat(result).isInstanceOf(DataResult.Error::class.java)

            val successData = (result as DataResult.Error).error
            assertThat(successData).isEqualTo(AuthenticationError.GetUserFirebaseException)
        }
    }

    @Test
    fun `When logout is called , then i should return DataResult Success with Unit`() {
        runTest {
            // GIVEN
            every { firebaseAuth.signOut() } just runs

            val result = authenticationRepository.logout()

            assertThat(result).isInstanceOf(DataResult.Success::class.java)

            val successData = (result as DataResult.Success).data
            assertThat(successData).isEqualTo(Unit)
        }
    }

    @Test
    fun `When logout is called , then i should return DataResult Error with LogoutFirebaseException`() {
        runTest {
            // GIVEN
            every { firebaseAuth.signOut() } throws Exception()

            val result = authenticationRepository.logout()

            assertThat(result).isInstanceOf(DataResult.Error::class.java)

            val successData = (result as DataResult.Error).error
            assertThat(successData).isEqualTo(AuthenticationError.LogoutFirebaseException)
        }
    }
}