package pl.dawidfendler.domain.use_case.home

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.DISPLAY_HOME
import javax.inject.Inject

class GetDisplayHomeUseCase @Inject constructor(
    private val dataStore: DataStore,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke() = flow {
        val item = dataStore.getPreferences(DISPLAY_HOME, false).firstOrNull()
        emit(item)
    }.flowOn(dispatcherProvider.io)
}
