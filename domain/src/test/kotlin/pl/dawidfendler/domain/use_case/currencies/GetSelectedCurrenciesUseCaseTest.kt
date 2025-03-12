package pl.dawidfendler.domain.use_case.currencies

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.SELECTED_CURRENCY
import pl.dawidfendler.domain.MainDispatcherRule
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE

class GetSelectedCurrenciesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var dataStore: DataStore
    private lateinit var getSelectedCurrenciesUseCase: GetSelectedCurrenciesUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        dataStore = mockk()
        getSelectedCurrenciesUseCase = GetSelectedCurrenciesUseCase(dataStore, dispatcherProvider)
    }

    @Test
    fun `When  getSelectedCurrencies is called, then return selected currency`() {
        runTest {
            // GIVEN
            val currency = POLISH_ZLOTY_CODE
            coEvery { dataStore.getPreferences(SELECTED_CURRENCY, POLISH_ZLOTY_CODE) } returns flowOf(currency)

            // WHEN
            getSelectedCurrenciesUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(currency)
                awaitComplete()
            }
        }
    }
}