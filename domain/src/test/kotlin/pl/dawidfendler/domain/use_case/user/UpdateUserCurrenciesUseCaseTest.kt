package pl.dawidfendler.domain.use_case.user

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
import pl.dawidfendler.domain.repository.UserRepository
import pl.dawidfendler.util.flow.DomainResult

class UpdateUserCurrenciesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: UserRepository
    private lateinit var updateUserCurrenciesUseCase: UpdateUserCurrenciesUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        updateUserCurrenciesUseCase = UpdateUserCurrenciesUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When updateUserCurrenciesUseCase is called, then return DomainResult Success`() {
        runTest {
            // GIVEN
            val currencies = listOf("PLN", "USD")
            coEvery { repository.getUserCurrencies() } returns currencies
            coEvery { repository.updateUserCurrencies(currencies) } just runs

            // WHEN
            updateUserCurrenciesUseCase.invoke(currencies).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When updateUserCurrenciesUseCase is called, then return DomainResult Error`() {
        runTest {
            // GIVEN
            val currencies = listOf("PLN", "USD")
            val exception = Exception()
            coEvery { repository.getUserCurrencies() } returns currencies
            coEvery { repository.updateUserCurrencies(currencies) } throws exception

            // WHEN
            updateUserCurrenciesUseCase.invoke(currencies).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Error(exception))
                awaitComplete()
            }
        }
    }
}