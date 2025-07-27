package pl.dawidfendler.domain.use_case.transaction

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
import pl.dawidfendler.domain.repository.finance_manager.TransactionRepository
import pl.dawidfendler.util.flow.DomainResult

class GetTransactionUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: TransactionRepository
    private lateinit var getTransactionUseCase: GetTransactionUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        getTransactionUseCase = GetTransactionUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When getTransaction is called, then return DomainResult Success list transaction`() {
        runTest {
            // GIVEN
            val transaction = listOf(transactionData)
            coEvery { repository.getTransaction() } returns transaction

            // WHEN
            getTransactionUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(transaction))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When getTransaction is called, then return DomainResult Error exception`() {
        runTest {
            // GIVEN
            val exception = Exception()
            coEvery { repository.getTransaction() } throws exception

            // WHEN
            getTransactionUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Error(exception))
                awaitComplete()
            }
        }
    }
}