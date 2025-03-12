package pl.dawidfendler.domain.use_case.currencies

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
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.repository.CurrenciesRepository
import pl.dawidfendler.domain.util.NoInternetException
import pl.dawidfendler.domain.util.RequestTimeException
import pl.dawidfendler.domain.util.ServerException
import pl.dawidfendler.domain.util.TooManyRequestException
import pl.dawidfendler.domain.util.UnknownException
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult
import pl.dawidfendler.util.network.NetworkError
import pl.dawidfendler.util.network.NetworkError.TOO_MANY_REQUESTS

class GetCurrenciesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: CurrenciesRepository
    private lateinit var getCurrenciesUseCase: GetCurrenciesUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        getCurrenciesUseCase = GetCurrenciesUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When getCurrencies is called, then return DomainResult Success list exchange rate table`() {
        runTest {
            // GIVEN
            val exchangeRateTable = listOf(exchangeRateTableData)
            coEvery { repository.getCurrenciesTableA() } returns DataResult.Success(exchangeRateTable)
            coEvery { repository.getCurrenciesTableB() } returns DataResult.Success(exchangeRateTable)

            // WHEN
            getCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(exchangeRateTable + exchangeRateTable))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When getCurrencies is called, then return DomainResult Success empty list currencies`() {
        runTest {
            // GIVEN
            coEvery { repository.getCurrenciesTableA() } returns DataResult.Success(null)
            coEvery { repository.getCurrenciesTableB() } returns DataResult.Success(null)

            // WHEN
            getCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(emptyList<ExchangeRateTable>()))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When  getCurrencies is called, then return DomainResult Error UNKNOWN`() {
        runTest {
            // GIVEN
            val exception = NetworkError.UNKNOWN
            coEvery { repository.getCurrenciesTableA() } returns  DataResult.Error(exception)
            coEvery { repository.getCurrenciesTableB() } returns  DataResult.Error(exception)

            // WHEN
            getCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isInstanceOf(DomainResult.Error::class.java)

                val error = (result as DomainResult.Error).error
                assertThat(error).isInstanceOf(UnknownException::class.java)
                awaitComplete()
            }
        }
    }

    @Test
    fun `When  getCurrencies is called, then return DomainResult Error TOO_MANY_REQUESTS`() {
        runTest {
            // GIVEN
            val exception = TOO_MANY_REQUESTS
            coEvery { repository.getCurrenciesTableA() } returns  DataResult.Error(exception)
            coEvery { repository.getCurrenciesTableB() } returns  DataResult.Error(exception)

            // WHEN
            getCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isInstanceOf(DomainResult.Error::class.java)

                val error = (result as DomainResult.Error).error
                assertThat(error).isInstanceOf(TooManyRequestException::class.java)
                awaitComplete()
            }
        }
    }

    @Test
    fun `When  getCurrencies is called, then return DomainResult Error REQUEST_TIMEOUT`() {
        runTest {
            // GIVEN
            val exception = NetworkError.REQUEST_TIMEOUT
            coEvery { repository.getCurrenciesTableA() } returns  DataResult.Error(exception)
            coEvery { repository.getCurrenciesTableB() } returns  DataResult.Error(exception)

            // WHEN
            getCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isInstanceOf(DomainResult.Error::class.java)

                val error = (result as DomainResult.Error).error
                assertThat(error).isInstanceOf(RequestTimeException::class.java)
                awaitComplete()
            }
        }
    }

    @Test
    fun `When  getCurrencies is called, then return DomainResult Error NO_INTERNET`() {
        runTest {
            // GIVEN
            val exception = NetworkError.NO_INTERNET
            coEvery { repository.getCurrenciesTableA() } returns  DataResult.Error(exception)
            coEvery { repository.getCurrenciesTableB() } returns  DataResult.Error(exception)

            // WHEN
            getCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isInstanceOf(DomainResult.Error::class.java)

                val error = (result as DomainResult.Error).error
                assertThat(error).isInstanceOf(NoInternetException::class.java)
                awaitComplete()
            }
        }
    }

    @Test
    fun `When  getCurrencies is called, then return DomainResult Error SERVER_ERROR`() {
        runTest {
            // GIVEN
            val exception = NetworkError.SERVER_ERROR
            coEvery { repository.getCurrenciesTableA() } returns  DataResult.Success(emptyList())
            coEvery { repository.getCurrenciesTableB() } returns  DataResult.Error(exception)

            // WHEN
            getCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isInstanceOf(DomainResult.Error::class.java)

                val error = (result as DomainResult.Error).error
                assertThat(error).isInstanceOf(ServerException::class.java)
                awaitComplete()
            }
        }
    }
}