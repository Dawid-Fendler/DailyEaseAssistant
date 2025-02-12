package pl.dawidfendler.domain.use_case.authentication_use_case

import android.util.Log
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.SELECTED_CURRENCY
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY
import javax.inject.Inject

class GetSelectedCurrenciesUseCase @Inject constructor(
    private val dataStore: DataStore,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke() = flow {
        dataStore.getPreferences(SELECTED_CURRENCY, POLISH_ZLOTY).onEach { result ->
            emit(result)
        }.catch { err ->
            Log.e("GetSelectedCurrenciesUseCase Exception", "$err")
            emit(POLISH_ZLOTY)
        }.collect()
    }.flowOn(dispatcherProvider.io)
}