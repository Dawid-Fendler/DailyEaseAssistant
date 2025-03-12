package pl.dawidfendler.domain.use_case.user

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
import pl.dawidfendler.domain.repository.UserRepository
import pl.dawidfendler.util.flow.DomainResult

class GetAccountBalanceUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: UserRepository
    private lateinit var getAccountBalanceUseCase: GetAccountBalanceUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        getAccountBalanceUseCase = GetAccountBalanceUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When getAccountBalance is called, then return DomainResult Success`() {
        runTest {
            // GIVEN
            val accountBalance = 500.0.toBigDecimal()
            coEvery { repository.getAccountBalance() } returns  accountBalance

            // WHEN
            getAccountBalanceUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(accountBalance))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When getAccountBalance is called, then return DomainResult Error`() {
        runTest {
            // GIVEN
            val exception = Exception()
            coEvery { repository.getAccountBalance() } throws exception

            // WHEN
            getAccountBalanceUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Error(exception))
                awaitComplete()
            }
        }
    }
}