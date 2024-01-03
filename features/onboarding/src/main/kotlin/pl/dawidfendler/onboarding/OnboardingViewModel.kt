package pl.dawidfendler.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.ONBOARDING_DISPLAYED
import pl.dawidfendler.onboarding.navigation.OnboardingNavigationRoute.ONBOARDING_SCREEN_ROUTE
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStore: DataStore,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var startDestination = ONBOARDING_SCREEN_ROUTE

    fun saveOnboardingDisplayed() {
        viewModelScope.launch(dispatcherProvider.io) {
            dataStore.putPreference(ONBOARDING_DISPLAYED, true)
        }
    }

    fun getOnboardingDisplayed() {
        viewModelScope.launch(dispatcherProvider.io) {
            dataStore.getPreferences(ONBOARDING_DISPLAYED, false)
                .onEach { onboardingWasDisplayed ->
                    startDestination = if (onboardingWasDisplayed) {
                        ""
                    } else {
                        ONBOARDING_SCREEN_ROUTE
                    }
                }.catch { err ->
                    startDestination = ONBOARDING_SCREEN_ROUTE
                    Timber.e(err, err.message)
                }.collect()
        }
    }
}
