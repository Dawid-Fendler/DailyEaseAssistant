package pl.dawidfendler.domain.use_case.home

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
import pl.dawidfendler.datastore.DataStoreConstants.DISPLAY_HOME
import pl.dawidfendler.domain.MainDispatcherRule

class GetDisplayHomeUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var dataStore: DataStore
    private lateinit var getDisplayHomeUseCase: GetDisplayHomeUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        dataStore = mockk()
        getDisplayHomeUseCase = GetDisplayHomeUseCase(dataStore, dispatcherProvider)
    }

    @Test
    fun `When  getDisplayHome is called, then return true`() {
        runTest {
            // GIVEN
            coEvery { dataStore.getPreferences(DISPLAY_HOME, false) } returns flowOf(true)

            // WHEN
            getDisplayHomeUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(true)
                awaitComplete()
            }
        }
    }

    @Test
    fun `When  getDisplayHome is called, then return false`() {
        runTest {
            // GIVEN
            coEvery { dataStore.getPreferences(DISPLAY_HOME, false) } returns flowOf(false)

            // WHEN
            getDisplayHomeUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(false)
                awaitComplete()
            }
        }
    }
}