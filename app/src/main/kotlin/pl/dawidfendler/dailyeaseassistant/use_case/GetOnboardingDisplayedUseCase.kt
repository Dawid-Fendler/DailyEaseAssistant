package pl.dawidfendler.dailyeaseassistant.use_case

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.ONBOARDING_DISPLAYED
import javax.inject.Inject

class GetOnboardingDisplayedUseCase @Inject constructor(
    private val dataStore: DataStore,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke() = flow {
        dataStore.getPreferences(ONBOARDING_DISPLAYED, false).onEach { result ->
            emit(result)
        }.catch { err ->
            throw err
        }.collect()
    }.flowOn(dispatcherProvider.io)
}