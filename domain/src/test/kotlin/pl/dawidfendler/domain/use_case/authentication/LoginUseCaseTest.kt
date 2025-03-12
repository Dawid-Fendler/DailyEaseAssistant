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
import pl.dawidfendler.domain.util.LoginFirebaseException
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult

class LoginUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: AuthenticationRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        loginUseCase = LoginUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When loginUseCase is called, then return DomainResult Success`() {
        runTest {
            // GIVEN
            val email = "email"
            val password = "password"
            coEvery { repository.login(email, password) } returns DataResult.Success(Unit)

            // WHEN
            loginUseCase.invoke(email, password).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When loginUseCase is called, then return DomainResult Error LoginFirebaseException`() {
        runTest {
            // GIVEN
            val email = "email"
            val password = "password"
            coEvery { repository.login(email, password) } returns DataResult.Error(AuthenticationError.LoginFirebaseException)

            // WHEN
            loginUseCase.invoke(email, password).test {
                val result = awaitItem()

                // Then
                assertThat(result).isInstanceOf(DomainResult.Error::class.java)

                val error = (result as DomainResult.Error).error
                assertThat(error).isInstanceOf(LoginFirebaseException::class.java)
                awaitComplete()
            }
        }
    }
}