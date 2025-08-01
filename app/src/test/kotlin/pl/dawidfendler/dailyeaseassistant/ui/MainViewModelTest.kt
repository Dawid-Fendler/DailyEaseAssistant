import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dawidfendler.dailyeaseassistant.ui.MainDispatcherRule
import pl.dawidfendler.dailyeaseassistant.ui.MainState
import pl.dawidfendler.dailyeaseassistant.ui.MainViewModel
import pl.dawidfendler.domain.use_case.home.GetDisplayHomeUseCase
import pl.dawidfendler.domain.use_case.onboarding.GetOnboardingDisplayedUseCase
import pl.dawidfendler.util.navigation.Destination


@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var getOnboardingDisplayedUseCase: GetOnboardingDisplayedUseCase
    private lateinit var getDisplayHomeUseCase: GetDisplayHomeUseCase

    @Before
    fun setUp() {
        getOnboardingDisplayedUseCase = mockk()
        getDisplayHomeUseCase = mockk()
        mainViewModel = MainViewModel(
            getOnboardingDisplayedUseCase = getOnboardingDisplayedUseCase,
            getDisplayHomeUseCase = getDisplayHomeUseCase
        )
    }

    @Test
    fun `When MainViewModel is initialize, then MainState has initial value`() {
        // GIVEN
        val mainState = mainViewModel.state

        // THEN
        assertThat(mainState.isStarting).isFalse()
        assertThat(mainState.navigation).isEqualTo(Destination.Onboarding)
    }

    @Test
    fun `When onStart is called, then getOnboardingDisplayedUseCase return OnboardingNavigation`() = runTest {
        // GIVEN
        coEvery { getOnboardingDisplayedUseCase() } returns flowOf(false)
        coEvery { getDisplayHomeUseCase() } returns flowOf(false)

        // WHEN
        mainViewModel.onStart()

        // THEN
        advanceUntilIdle()
        assertThat(mainViewModel.state).isEqualTo(
            MainState(
                navigation = Destination.Onboarding,
                isStarting = true
            )
        )
        verify(exactly = 1) { getOnboardingDisplayedUseCase() }
        verify(exactly = 1) { getDisplayHomeUseCase() }
    }

//    @Test
//    fun `When onStart is called, then getOnboardingDisplayedUseCase return LoginNavigation`() = runTest {
//        // GIVEN
//        coEvery { getOnboardingDisplayedUseCase() } returns flowOf(true)
//        coEvery { getDisplayHomeUseCase() } returns flowOf(false)
//
//        // WHEN
//        mainViewModel.onStart()
//
//        // THEN
//        advanceUntilIdle()
//        assertThat(mainViewModel.state).isEqualTo(
//            MainState(
//                navigation = Navigation.LoginNavigation,
//                isStarting = true
//            )
//        )
//        verify(exactly = 1) { getOnboardingDisplayedUseCase() }
//        verify(exactly = 1) { getDisplayHomeUseCase() }
//    }

    @Test
    fun `When onStart is called, then getOnboardingDisplayedUseCase return HomeNavigation`() = runTest {
        // GIVEN
        coEvery { getOnboardingDisplayedUseCase() } returns flowOf(false)
        coEvery { getDisplayHomeUseCase() } returns flowOf(true)

        // WHEN
        mainViewModel.onStart()

        // THEN
        advanceUntilIdle()
        assertThat(mainViewModel.state).isEqualTo(
            MainState(
                navigation = Destination.Home,
                isStarting = true
            )
        )
        verify(exactly = 1) { getOnboardingDisplayedUseCase() }
        verify(exactly = 1) { getDisplayHomeUseCase() }
    }
}