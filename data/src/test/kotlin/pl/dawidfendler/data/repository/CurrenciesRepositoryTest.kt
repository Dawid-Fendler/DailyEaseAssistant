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
import pl.dawidfendler.data.datasource.local.currencies.CurrenciesLocalDataSource
import pl.dawidfendler.data.datasource.remote.currencies.CurrenciesRemoteDataSource
import pl.dawidfendler.data.mapper.toDomain
import pl.dawidfendler.data.model.common.responseBodyDataTest
import pl.dawidfendler.data.model.currencies.exchangeRateTableData
import pl.dawidfendler.data.model.currencies.exchangeRateTableEntityTest
import pl.dawidfendler.data.model.currencies.exchangeRateTableResponseData
import pl.dawidfendler.data.model.currency.remote.ExchangeRateTableResponse
import pl.dawidfendler.data.repository.finance_manager.CurrenciesRepositoryImpl
import pl.dawidfendler.date.DateTimeUtils
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.logger.Logger
import pl.dawidfendler.util.network.NetworkError
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException

class CurrenciesRepositoryTest {

    private lateinit var currenciesRemoteDataSource: CurrenciesRemoteDataSource
    private lateinit var currenciesLocalDataSource: CurrenciesLocalDataSource
    private lateinit var dateTimeUtils: DateTimeUtils
    private lateinit var currenciesRepository: CurrenciesRepositoryImpl
    private lateinit var logger: Logger

    @Before
    fun setUp() {
        currenciesLocalDataSource = mockk(relaxed = true)
        currenciesRemoteDataSource = mockk(relaxed = true)
        logger = mockk(relaxed = true)
        dateTimeUtils = mockk(relaxed = true)
        currenciesRepository = CurrenciesRepositoryImpl(
            currenciesRemoteDataSource = currenciesRemoteDataSource,
            currenciesLocalDataSource = currenciesLocalDataSource,
            dateTimeUtils = dateTimeUtils,
            logger = logger
        )
    }

    @Test
    fun `When getCurrenciesTableA is called, lastUpdateDate is false and currencies from database is empty, then it should save data to database and return data result success`() {
        runTest {
            // GIVEN
            val currenciesResponse = listOf(exchangeRateTableResponseData)
            val expectedResult = DataResult.Success(listOf(exchangeRateTableData))

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns false
            coEvery { currenciesRemoteDataSource.getTableA() } returns Response.success(currenciesResponse)
            coEvery { currenciesLocalDataSource.deleteCurrencies() } just runs
            coEvery { currenciesLocalDataSource.insertAll(any()) } just runs

            // When
            val result = currenciesRepository.getCurrenciesTableA()

            coVerify { currenciesLocalDataSource.deleteCurrencies() }
            coVerify { currenciesLocalDataSource.insertAll(match { it.isNotEmpty() }) }
            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableA is called, lastUpdateDate is false and currencies from database is not empty, then it should return data result success from database`() {
        runTest {
            // GIVEN
            val currenciesEntity = listOf(exchangeRateTableEntityTest)
            val expectedResult = DataResult.Success(listOf(exchangeRateTableData))

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns currenciesEntity
            coEvery { dateTimeUtils.isToday(any()) } returns false

            // When
            val result = currenciesRepository.getCurrenciesTableA()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableA is called, lastUpdateDate is true and currencies from database is not empty, then it should save data to database and return data result success`() {
        runTest {
            // GIVEN
            val currenciesEntity = listOf(exchangeRateTableEntityTest)
            val currenciesResponse = listOf(exchangeRateTableResponseData)
            val expectedResult = DataResult.Success(listOf(exchangeRateTableData))

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns currenciesEntity
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableA() } returns Response.success(currenciesResponse)
            coEvery { currenciesLocalDataSource.deleteCurrencies() } just runs
            coEvery { currenciesLocalDataSource.insertAll(any()) } just runs

            // When
            val result = currenciesRepository.getCurrenciesTableA()

            coVerify { currenciesLocalDataSource.deleteCurrencies() }
            coVerify { currenciesLocalDataSource.insertAll(match { it.isNotEmpty() }) }
            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableA is called, lastUpdateDate is true and currencies from database is empty, then api throw UnresolvedAddressException and should return data result error NO_INTERNET`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.NO_INTERNET)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableA() } throws UnresolvedAddressException()

            // When
            val result = currenciesRepository.getCurrenciesTableA()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableA is called, lastUpdateDate is true and currencies from database is empty, then api throw Exception and should return data result error UNKNOWN`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.UNKNOWN)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableA() } throws Exception()

            // When
            val result = currenciesRepository.getCurrenciesTableA()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableA is called, lastUpdateDate is true and currencies from database is empty, then it should return data result error REQUEST_TIMEOUT`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.REQUEST_TIMEOUT)
            val responseResult = Response.error<List<ExchangeRateTableResponse>>(408, responseBodyDataTest)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableA() } returns  responseResult

            // When
            val result = currenciesRepository.getCurrenciesTableA()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableA is called, lastUpdateDate is true and currencies from database is empty, then it should return data result error TOO_MANY_REQUESTS`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.TOO_MANY_REQUESTS)
            val responseResult = Response.error<List<ExchangeRateTableResponse>>(429, responseBodyDataTest)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableA() } returns  responseResult

            // When
            val result = currenciesRepository.getCurrenciesTableA()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableA is called, lastUpdateDate is true and currencies from database is empty, then it should return data result error SERVER_ERROR`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.SERVER_ERROR)
            val responseResult = Response.error<List<ExchangeRateTableResponse>>(504, responseBodyDataTest)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableA() } returns  responseResult

            // When
            val result = currenciesRepository.getCurrenciesTableA()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableA is called, lastUpdateDate is true and currencies from database is empty, then it should return data result error UNKNOWN`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.UNKNOWN)
            val responseResult = Response.error<List<ExchangeRateTableResponse>>(404, responseBodyDataTest)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableA() } returns  responseResult

            // When
            val result = currenciesRepository.getCurrenciesTableA()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableB is called, lastUpdateDate is true, then it should save data to database and return data result success`() {
        runTest {
            // GIVEN
            val currenciesResponse = listOf(exchangeRateTableResponseData)
            val expectedResult = DataResult.Success(listOf(exchangeRateTableData))

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableB() } returns Response.success(currenciesResponse)
            coEvery { currenciesLocalDataSource.insertAll(any()) } just runs

            // When
            val result = currenciesRepository.getCurrenciesTableB()

            coVerify { currenciesLocalDataSource.insertAll(match { it.isNotEmpty() }) }
            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableB is called, lastUpdateDate is false, then it should save data to database and return data result success`() {
        runTest {
            // GIVEN
            val currenciesEntity = listOf(exchangeRateTableEntityTest.copy(
                tableName = "TableB"
            ))
            val expectedResult = DataResult.Success(listOf<ExchangeRateTable>())

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns currenciesEntity
            coEvery { dateTimeUtils.isToday(any()) } returns false

            // When
            val result = currenciesRepository.getCurrenciesTableB()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableB is called, lastUpdateDate is true, then api throw UnresolvedAddressException it should return data result error NO_INTERNET`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.NO_INTERNET)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableB() } throws UnresolvedAddressException()

            // When
            val result = currenciesRepository.getCurrenciesTableB()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableB is called, lastUpdateDate is true, then api throw Exception it should return data result error UNKNOWN`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.UNKNOWN)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableB() } throws Exception()

            // When
            val result = currenciesRepository.getCurrenciesTableB()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableB is called, lastUpdateDate is true, then it should return data result error REQUEST_TIMEOUT`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.REQUEST_TIMEOUT)
            val responseResult = Response.error<List<ExchangeRateTableResponse>>(408, responseBodyDataTest)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableB() } returns  responseResult

            // When
            val result = currenciesRepository.getCurrenciesTableB()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableB is called, lastUpdateDate is true, then it should return data result error TOO_MANY_REQUESTS`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.TOO_MANY_REQUESTS)
            val responseResult = Response.error<List<ExchangeRateTableResponse>>(429, responseBodyDataTest)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableB() } returns  responseResult

            // When
            val result = currenciesRepository.getCurrenciesTableB()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableB is called, lastUpdateDate is true, then it should return data result error SERVER_ERROR`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.SERVER_ERROR)
            val responseResult = Response.error<List<ExchangeRateTableResponse>>(504, responseBodyDataTest)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableB() } returns  responseResult

            // When
            val result = currenciesRepository.getCurrenciesTableB()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesTableB is called, lastUpdateDate is true, then it should return data result error UNKNOWN`() {
        runTest {
            // GIVEN
            val expectedResult = DataResult.Error(NetworkError.UNKNOWN)
            val responseResult = Response.error<List<ExchangeRateTableResponse>>(404, responseBodyDataTest)

            coEvery { currenciesLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { dateTimeUtils.isToday(any()) } returns true
            coEvery { currenciesRemoteDataSource.getTableA() } returns  responseResult

            // When
            val result = currenciesRepository.getCurrenciesTableA()

            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When getCurrenciesByCode is called, then it should return exchange rate table`() {
        runTest {
            // Given
            val exchangeEntity = exchangeRateTableEntityTest
            val currencyCode = "USD"
            coEvery { currenciesLocalDataSource.getCurrencyByCode(currencyCode) } returns exchangeEntity

            // When
            val result = currenciesRepository.getCurrenciesByCode(currencyCode)

            // Then
            assertThat(result).isEqualTo(exchangeEntity.toDomain())
        }
    }

    @Test
    fun `When getCurrenciesByCode is called, then it should return null`() {
        runTest {
            // Given
            val currencyCode = "USD"
            coEvery { currenciesLocalDataSource.getCurrencyByCode(currencyCode) } returns null

            // When
            val result = currenciesRepository.getCurrenciesByCode(currencyCode)

            // Then
            assertThat(result).isNull()
        }
    }

    @Test
    fun `When deleteCurrencies is called, then it should call deleteCurrencies on the datasource`() {
        runTest {
            // Given
            coEvery { currenciesLocalDataSource.deleteCurrencies() } just runs

            // When
            currenciesRepository.deleteCurrencies()

            // Then
            coVerify { currenciesLocalDataSource.deleteCurrencies() }
        }
    }
}