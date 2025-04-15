package pl.dawidfendler.domain.use_case.user

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
import pl.dawidfendler.domain.repository.UserRepository
import pl.dawidfendler.util.flow.DomainResult

class GetUserCurrenciesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: UserRepository
    private lateinit var getUserCurrenciesUseCase: GetUserCurrenciesUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        getUserCurrenciesUseCase = GetUserCurrenciesUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When getUserCurrencies is called, then return DomainResult Success`() {
        runTest {
            // GIVEN
            val userCurrencies = listOf("PLN", "USD")
            coEvery { repository.getUserCurrencies() } returns  userCurrencies

            // WHEN
            getUserCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(userCurrencies))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When getUserCurrencies is called, then return DomainResult Error`() {
        runTest {
            // GIVEN
            val exception = Exception()
            coEvery { repository.getUserCurrencies() } throws exception

            // WHEN
            getUserCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Error(exception))
                awaitComplete()
            }
        }
    }
}