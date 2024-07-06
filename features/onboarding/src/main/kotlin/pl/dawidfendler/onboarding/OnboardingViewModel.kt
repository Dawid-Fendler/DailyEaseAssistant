package pl.dawidfendler.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.ONBOARDING_DISPLAYED
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val dataStore: DataStore,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    fun saveOnboardingDisplayed() {
        viewModelScope.launch(dispatcherProvider.io) {
            dataStore.putPreference(ONBOARDING_DISPLAYED, true)
        }
    }
}
