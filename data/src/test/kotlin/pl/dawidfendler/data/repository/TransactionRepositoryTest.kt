package pl.dawidfendler.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import pl.dawidfendler.data.datasource.local.transaction.TransactionLocalDataSource
import pl.dawidfendler.data.mapper.toDomain
import pl.dawidfendler.data.mapper.toEntity
import pl.dawidfendler.data.model.transaction.TransactionEntity
import pl.dawidfendler.data.model.transaction.transactionEntityTest
import pl.dawidfendler.data.model.transaction.transactionTest
import pl.dawidfendler.data.repository.finance_manager.TransactionRepositoryImpl

class TransactionRepositoryTest {

    private lateinit var transactionLocalDataSource: TransactionLocalDataSource
    private lateinit var transactionRepository: TransactionRepositoryImpl

    @Before
    fun setUp() {
        transactionLocalDataSource = mockk(relaxed = true)
        transactionRepository = TransactionRepositoryImpl(transactionLocalDataSource)
    }

    @Test
    fun `When insert is called, then it should call insert on the datasource`() {
        runTest {
            // Given
            val transaction = transactionTest

            // When
            transactionLocalDataSource.insert(transaction.toEntity())

            // Then
            coVerify { transactionRepository.insert(transaction) }
        }
    }

    @Test
    fun `When getTransaction is called, then it should return list of transaction`() {
        runTest {
            // Given
            val transaction = listOf(transactionEntityTest)
            coEvery { transactionLocalDataSource.getTransaction() } returns transaction

            // When
            val result = transactionRepository.getTransaction()

            // Then
            assertThat(transaction.map { it.toDomain() }).isEqualTo(result)
        }
    }

    @Test
    fun `When getTransaction is called, then it should return empty list of transaction`() {
        runTest {
            // Given
            val transaction = emptyList<TransactionEntity>()
            coEvery { transactionLocalDataSource.getTransaction() } returns transaction

            // When
            val result = transactionRepository.getTransaction()

            // Then
            assertThat(transaction.map { it.toDomain() }).isEqualTo(result)
        }
    }

    @Test
    fun `When deleteTransaction is called, then it should call deleteTransaction on the datasource`() {
        runTest {
            // Given
            coEvery { transactionLocalDataSource.deleteTransaction() } just runs

            // When
            transactionRepository.deleteTransaction()

            // Then
            coVerify { transactionLocalDataSource.deleteTransaction() }
        }
    }
}