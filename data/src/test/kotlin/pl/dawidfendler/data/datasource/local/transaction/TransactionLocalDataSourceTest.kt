package pl.dawidfendler.data.datasource.local.transaction

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import pl.dawidfendler.data.database.finance_manger.transaction.TransactionDao
import pl.dawidfendler.data.model.transaction.transactionEntityTest

class TransactionLocalDataSourceTest {

    private lateinit var transactionDao: TransactionDao
    private lateinit var transactionLocalDataSource: TransactionLocalDataSourceImpl

    @Before
    fun setUp() {
        transactionDao = mockk(relaxed = true)
        transactionLocalDataSource = TransactionLocalDataSourceImpl(transactionDao)
    }

    @Test
    fun `When insert is called, then it should call insert on the dao`() {
        runTest {
            // Given
            val transaction = transactionEntityTest

            // When
            transactionLocalDataSource.insert(transaction)

            // Then
            coVerify { transactionDao.insert(transaction) }
        }
    }

    @Test
    fun `When getTransaction is called, then it should return the list of transactions`() {
        runTest {
            // Given
            val transactions = listOf(transactionEntityTest)
            coEvery { transactionDao.getTransaction() } returns transactions

            // When
            val result = transactionLocalDataSource.getTransaction()

            // Then
            assertThat(transactions).isEqualTo(result)
        }
    }

    @Test
    fun `When getTransaction is called, then it should return the empty list of transactions`() {
        runTest {
            // Given
            val transactions = listOf(transactionEntityTest)
            coEvery { transactionDao.getTransaction() } returns transactions

            // When
            val result = transactionLocalDataSource.getTransaction()

            // Then
            assertThat(transactions).isEqualTo(result)
        }
    }

    @Test
    fun `When deleteTransaction is called, then it should call deleteTransaction on the dao`() {
        runTest {
            // Given
            coEvery { transactionDao.deleteTransaction() } just runs

            // When
            transactionLocalDataSource.deleteTransaction()

            // Then
            coVerify { transactionDao.deleteTransaction() }
        }
    }
}