package pl.dawidfendler.domain.use_case.transaction

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.MainDispatcherRule
import pl.dawidfendler.domain.repository.TransactionRepository
import pl.dawidfendler.util.flow.DomainResult

class DeleteTransactionsUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: TransactionRepository
    private lateinit var deleteTransactionsUseCase: DeleteTransactionsUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        deleteTransactionsUseCase = DeleteTransactionsUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When deleteTransaction is called, then return DomainResult Success`() {
        runTest {
            // GIVEN
            coEvery { repository.deleteTransaction() } just runs

            // WHEN
            deleteTransactionsUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When deleteTransaction is called, then return DomainResult Error exception`() {
        runTest {
            // GIVEN
            val exception = Exception()
            coEvery { repository.deleteTransaction() } throws exception

            // WHEN
            deleteTransactionsUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Error(exception))
                awaitComplete()
            }
        }
    }
}