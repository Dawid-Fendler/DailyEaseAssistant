package pl.dawidfendler.domain.use_case.authentication

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.MainDispatcherRule
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.domain.util.AuthenticationError
import pl.dawidfendler.domain.util.RegistrationFirebaseException
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult

class RegistrationUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: AuthenticationRepository
    private lateinit var registrationUseCase: RegistrationUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        registrationUseCase = RegistrationUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When registrationUseCase is called, then return DomainResult Success`() {
        runTest {
            // GIVEN
            val email = "email"
            val password = "password"
            coEvery { repository.registerUser(email, password) } returns DataResult.Success(Unit)

            // WHEN
            registrationUseCase.invoke(email, password).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When registrationUseCase is called, then return DomainResult Error RegistrationFirebaseException`() {
        runTest {
            // GIVEN
            val email = "email"
            val password = "password"
            coEvery { repository.registerUser(email, password) } returns DataResult.Error(
                AuthenticationError.RegistrationFirebaseException)

            // WHEN
            registrationUseCase.invoke(email, password).test {
                val result = awaitItem()

                // Then
                assertThat(result).isInstanceOf(DomainResult.Error::class.java)

                val error = (result as DomainResult.Error).error
                assertThat(error).isInstanceOf(RegistrationFirebaseException::class.java)
                awaitComplete()
            }
        }
    }
}