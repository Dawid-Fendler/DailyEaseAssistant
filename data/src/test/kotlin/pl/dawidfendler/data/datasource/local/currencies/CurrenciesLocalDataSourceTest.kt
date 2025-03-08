package pl.dawidfendler.data.datasource.local.currencies

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import pl.dawidfendler.data.database.finance_manger.currencies.CurrenciesDao
import pl.dawidfendler.data.model.currencies.exchangeRateTableEntityTest

class CurrenciesLocalDataSourceTest {

    private lateinit var currenciesDao: CurrenciesDao
    private lateinit var currenciesLocalDataSource: CurrenciesLocalDataSourceImpl

    @Before
    fun setUp() {
        currenciesDao = mockk(relaxed = true)
        currenciesLocalDataSource = CurrenciesLocalDataSourceImpl(currenciesDao)
    }

    @Test
    fun `When insertAll is called, then it should call insertAll on the dao`() {
        runTest {
            // Given
            val currencies = listOf(exchangeRateTableEntityTest, exchangeRateTableEntityTest)

            // When
            currenciesLocalDataSource.insertAll(currencies)

            // Then
            coVerify { currenciesDao.insertAll(currencies) }
        }
    }

    @Test
    fun `When getAllCurrencies is called, then it should return the list of currencies`() {
        runTest {
            // Given
            val currencies = listOf(exchangeRateTableEntityTest, exchangeRateTableEntityTest)
            coEvery { currenciesDao.getAllCurrencies() } returns currencies

            // When
            val result = currenciesLocalDataSource.getAllCurrencies()

            // Then
            assertThat(currencies).isEqualTo(result)
        }
    }

    @Test
    fun `When getAllCurrencies is called, then it should return the empty currencies list`() {
        runTest {
            // Given
            coEvery { currenciesDao.getAllCurrencies() } returns emptyList()

            // When
            val result = currenciesLocalDataSource.getAllCurrencies()

            // Then
            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `When getCurrencyByCode is called, then it should return the correct currency`() {
        runTest {
            // Given
            val currency = exchangeRateTableEntityTest
            coEvery { currenciesDao.getCurrencyByCode("USD") } returns currency

            // When
            val result = currenciesLocalDataSource.getCurrencyByCode("USD")

            // Then
            assertThat(currency).isEqualTo(result)
        }
    }

    @Test
    fun `When getCurrencyByCode is called, then it should return null`() {
        runTest {
            // Given
            coEvery { currenciesDao.getCurrencyByCode("GBP") } returns null

            // When
            val result = currenciesLocalDataSource.getCurrencyByCode("GBP")

            // Then
            assertThat(result).isNull()
        }
    }

    @Test
    fun `When deleteCurrencies is called, then it should call deleteCurrencies on the dao`() {
        runTest {
            // Given
            coEvery { currenciesDao.deleteCurrencies() } just runs

            // When
            currenciesLocalDataSource.deleteCurrencies()

            // Then
            coVerify { currenciesDao.deleteCurrencies() }
        }
    }
}