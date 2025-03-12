package pl.dawidfendler.domain.use_case.onboarding

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
import pl.dawidfendler.datastore.DataStoreConstants.ONBOARDING_DISPLAYED
import pl.dawidfendler.domain.MainDispatcherRule

class GetOnboardingDisplayedUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var dataStore: DataStore
    private lateinit var getOnboardingDisplayedUseCase: GetOnboardingDisplayedUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        dataStore = mockk()
        getOnboardingDisplayedUseCase = GetOnboardingDisplayedUseCase(dataStore, dispatcherProvider)
    }

    @Test
    fun `When getOnboardingDisplayed is called, then return true`() {
        runTest {
            // GIVEN
            coEvery { dataStore.getPreferences(ONBOARDING_DISPLAYED, false) } returns flowOf(true)

            // WHEN
            getOnboardingDisplayedUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(true)
                awaitComplete()
            }
        }
    }

    @Test
    fun `When getOnboardingDisplayed is called, then return false`() {
        runTest {
            // GIVEN
            coEvery { dataStore.getPreferences(ONBOARDING_DISPLAYED, false) } returns flowOf(false)

            // WHEN
            getOnboardingDisplayedUseCase.invoke().test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(false)
                awaitComplete()
            }
        }
    }
}