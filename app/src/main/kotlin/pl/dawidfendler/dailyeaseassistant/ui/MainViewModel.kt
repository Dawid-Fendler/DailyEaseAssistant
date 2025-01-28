package pl.dawidfendler.dailyeaseassistant.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.dawidfendler.domain.use_case.home_use_case.GetDisplayHomeUseCase
import pl.dawidfendler.domain.use_case.onboarding_use_case.GetOnboardingDisplayedUseCase
import pl.dawidfendler.util.navigation.Navigation
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getOnboardingDisplayedUseCase: GetOnboardingDisplayedUseCase,
    private val getDisplayHomeUseCase: GetDisplayHomeUseCase
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    fun onStart() {
        combine(
            getOnboardingDisplayedUseCase(),
            getDisplayHomeUseCase()
        ) { onboardingWasDisplayed, displayHome ->
            Pair(onboardingWasDisplayed, displayHome)
        }.onEach { result ->
            state = if (result.second) {
                state.copy(navigation = Navigation.HomeNavigation, isStarting = true)
            } else if (result.first) {
                state.copy(navigation = Navigation.HomeNavigation, isStarting = true)
            } else {
                state.copy(navigation = Navigation.OnboardingNavigation, isStarting = true)
            }
        }.launchIn(viewModelScope)
    }
}
