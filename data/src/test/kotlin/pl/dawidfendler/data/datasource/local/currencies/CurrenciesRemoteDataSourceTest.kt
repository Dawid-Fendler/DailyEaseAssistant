package pl.dawidfendler.data.datasource.local.currencies

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import pl.dawidfendler.data.datasource.remote.currencies.CurrenciesRemoteDataSourceImpl
import pl.dawidfendler.data.model.currencies.exchangeRateTableResponseData
import pl.dawidfendler.data.model.currency.remote.ExchangeRateTableResponse
import pl.dawidfendler.data.service.currency.CurrenciesApi
import retrofit2.Response

class CurrenciesRemoteDataSourceTest {

    private lateinit var currenciesApi: CurrenciesApi
    private lateinit var currenciesRemoteDataSource: CurrenciesRemoteDataSourceImpl

    @Before
    fun setUp() {
        currenciesApi = mockk(relaxed = true)
        currenciesRemoteDataSource = CurrenciesRemoteDataSourceImpl(currenciesApi)
    }

    @Test
    fun `When getTableA is called, then it should return the response list of currencies from table A`() {
        runTest {
            // Given
            val currencies = listOf(exchangeRateTableResponseData)
            coEvery { currenciesApi.getTableA() } returns Response.success(currencies)

            // When
            val result = currenciesRemoteDataSource.getTableA()

            // Then
            assertThat(currencies).isEqualTo(result.body())
        }
    }

    @Test
    fun `When getTableA is called, then it should return error from api`() {
        runTest {
            // Given
            val mockReturns = Response.error<List<ExchangeRateTableResponse>>(
                404,
                "Bad Request".toResponseBody("text/plain".toMediaType())
            )
            coEvery { currenciesApi.getTableA() } returns mockReturns

            // When
            val result = currenciesRemoteDataSource.getTableA()

            // Then
            assertThat(result.body()).isNull()
            assertThat(result.code()).isEqualTo(404)
        }
    }

    @Test
    fun `When getTableB is called, then it should return the response list of currencies from table B`() {
        runTest {
            // Given
            val currencies = listOf(exchangeRateTableResponseData)
            coEvery { currenciesApi.getTableB() } returns Response.success(currencies)

            // When
            val result = currenciesRemoteDataSource.getTableB()

            // Then
            assertThat(currencies).isEqualTo(result.body())
        }
    }

    @Test
    fun `When getTableB is called, then it should return error from api`() {
        runTest {
            // Given
            val mockReturns = Response.error<List<ExchangeRateTableResponse>>(
                404,
                "Bad Request".toResponseBody("text/plain".toMediaType())
            )
            coEvery { currenciesApi.getTableB() } returns mockReturns

            // When
            val result = currenciesRemoteDataSource.getTableB()

            // Then
            assertThat(result.body()).isNull()
            assertThat(result.code()).isEqualTo(404)
        }
    }
}