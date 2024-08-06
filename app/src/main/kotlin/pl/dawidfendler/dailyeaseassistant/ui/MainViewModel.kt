package pl.dawidfendler.dailyeaseassistant.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.dawidfendler.domain.use_case.onboarding_use_case.GetOnboardingDisplayedUseCase
import pl.dawidfendler.util.navigation.Navigation
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getOnboardingDisplayedUseCase: GetOnboardingDisplayedUseCase
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        getOnboardingDisplayedUseCase().onEach { result ->
            state = if (result) {
                state.copy(navigation = Navigation.LoginNavigation, isStarting = true)
            } else {
                state.copy(navigation = Navigation.OnboardingNavigation, isStarting = true)
            }
        }.launchIn(viewModelScope)
    }
}
