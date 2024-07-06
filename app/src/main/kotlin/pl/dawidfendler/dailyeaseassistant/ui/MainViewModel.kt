package pl.dawidfendler.dailyeaseassistant.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import pl.dawidfendler.dailyeaseassistant.use_case.GetOnboardingDisplayedUseCase
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.navigation.Navigation
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getOnboardingDisplayedUseCase: GetOnboardingDisplayedUseCase
) : ViewModel() {

    val getStartDestination: StateFlow<DataResult<Navigation>>
        get() = getOnboardingDisplayedUseCase().flatMapLatest { result ->
            if (result) {
                flowOf(DataResult.Success(Navigation.LoginNavigation))

            } else {
                flowOf(DataResult.Success(Navigation.OnboardingNavigation))
            }
        }.catch { err ->
            flowOf(DataResult.Error(err))
        }.stateIn(
            scope = viewModelScope,
            initialValue = DataResult.Initial,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
        )
}