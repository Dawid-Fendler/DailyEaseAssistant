package pl.dawidfendler.domain.use_case.currencies

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
import pl.dawidfendler.domain.repository.CurrenciesRepository
import pl.dawidfendler.util.flow.DomainResult

class DeleteCurrenciesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: CurrenciesRepository
    private lateinit var deleteCurrenciesUseCase: DeleteCurrenciesUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        deleteCurrenciesUseCase = DeleteCurrenciesUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When deleteCurrencies is called, then return DomainResult Success`() {
        runTest {
            // GIVEN
            coEvery { repository.deleteCurrencies() } just runs

            // WHEN
            deleteCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When deleteCurrencies is called, then return DomainResult Error exception`() {
        runTest {
            // GIVEN
            val exception = Exception()
            coEvery { repository.deleteCurrencies() } throws exception

            // WHEN
            deleteCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Error(exception))
                awaitComplete()
            }
        }
    }
}