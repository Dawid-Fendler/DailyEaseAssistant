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
import pl.dawidfendler.domain.util.LogoutFirebaseException
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult

class LogoutUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: AuthenticationRepository
    private lateinit var logoutUseCase: LogoutUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        logoutUseCase = LogoutUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When logoutUseCase is called, then return DomainResult Success`() {
        runTest {
            // GIVEN
            coEvery { repository.logout() } returns DataResult.Success(Unit)

            // WHEN
            logoutUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When logoutUseCase is called, then return DomainResult Error LogoutFirebaseException`() {
        runTest {
            // GIVEN
            coEvery { repository.logout() } returns DataResult.Error(
                AuthenticationError.LogoutFirebaseException)

            // WHEN
            logoutUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isInstanceOf(DomainResult.Error::class.java)

                val error = (result as DomainResult.Error).error
                assertThat(error).isInstanceOf(LogoutFirebaseException::class.java)
                awaitComplete()
            }
        }
    }
}