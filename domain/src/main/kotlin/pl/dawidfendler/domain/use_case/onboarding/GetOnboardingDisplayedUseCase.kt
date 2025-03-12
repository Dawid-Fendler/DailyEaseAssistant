package pl.dawidfendler.domain.use_case.onboarding

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.DISPLAY_HOME
import pl.dawidfendler.datastore.DataStoreConstants.ONBOARDING_DISPLAYED
import javax.inject.Inject

class GetOnboardingDisplayedUseCase @Inject constructor(
    private val dataStore: DataStore,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke() = flow {
        val item = dataStore.getPreferences(ONBOARDING_DISPLAYED, false).firstOrNull()
        emit(item)
    }.flowOn(dispatcherProvider.io)
}
