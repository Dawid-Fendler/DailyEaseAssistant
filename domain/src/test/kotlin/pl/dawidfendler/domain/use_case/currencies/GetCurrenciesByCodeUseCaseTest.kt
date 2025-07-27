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
import pl.dawidfendler.domain.repository.finance_manager.CurrenciesRepository
import pl.dawidfendler.util.flow.DomainResult

class GetCurrenciesByCodeUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: CurrenciesRepository
    private lateinit var getCurrenciesByCodeUseCase: GetCurrenciesByCodeUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        getCurrenciesByCodeUseCase = GetCurrenciesByCodeUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When getCurrenciesByCode is called, then return DomainResult Success exchange rate table`() {
        runTest {
            // GIVEN
            val code = "USD"
            val exchangeRateTable = exchangeRateTableData
            coEvery { repository.getCurrenciesByCode(code) } returns  exchangeRateTable

            // WHEN
            getCurrenciesByCodeUseCase.invoke(code).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(exchangeRateTable))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When getCurrenciesByCode is called, then return DomainResult Error exception`() {
        runTest {
            // GIVEN
            val code = "USD"
            val exception = Exception()
            coEvery { repository.getCurrenciesByCode(code) } throws exception

            // WHEN
            getCurrenciesByCodeUseCase.invoke(code).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Error(exception))
                awaitComplete()
            }
        }
    }
}