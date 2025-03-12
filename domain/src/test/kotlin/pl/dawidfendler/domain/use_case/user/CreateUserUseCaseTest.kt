package pl.dawidfendler.domain.use_case.user

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.MainDispatcherRule
import pl.dawidfendler.domain.repository.UserRepository
import pl.dawidfendler.util.flow.DomainResult

class CreateUserUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: UserRepository
    private lateinit var createUserUseCase: CreateUserUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        createUserUseCase = CreateUserUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When insertUser is called, then return DomainResult Success`() {
        runTest {
            // GIVEN
            val user = userData
            coEvery { repository.insertUser(user) } just Runs

            // WHEN
            createUserUseCase.invoke(user).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When insertUser is called, then return DomainResult Error`() {
        runTest {
            // GIVEN
            val user = userData
            val exception = Exception()
            coEvery { repository.insertUser(user) } throws exception

            // WHEN
            createUserUseCase.invoke(user).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Error(exception))
                awaitComplete()
            }
        }
    }
}