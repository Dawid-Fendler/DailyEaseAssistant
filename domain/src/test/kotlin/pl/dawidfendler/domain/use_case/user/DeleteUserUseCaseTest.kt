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

class DeleteUserUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: UserRepository
    private lateinit var deleteUseCaseTest: DeleteUserUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        deleteUseCaseTest = DeleteUserUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When deleteUser is called, then return DomainResult Success`() {
        runTest {
            // GIVEN
            coEvery { repository.deleteUser() } just Runs

            // WHEN
            deleteUseCaseTest.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When deleteUser is called, then return DomainResult Error`() {
        runTest {
            // GIVEN
            val exception = Exception()
            coEvery { repository.deleteUser() } throws exception

            // WHEN
            deleteUseCaseTest.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Error(exception))
                awaitComplete()
            }
        }
    }
}