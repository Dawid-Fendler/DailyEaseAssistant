package pl.dawidfendler.domain.use_case.currencies

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.SELECTED_CURRENCY
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE

class GetSelectedCurrenciesUseCase(
    private val dataStore: DataStore,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke() = flow {
        val selectedCurrency =
            dataStore.getPreferences(SELECTED_CURRENCY, POLISH_ZLOTY_CODE).firstOrNull()
        emit(selectedCurrency)
    }.flowOn(dispatcherProvider.io)
}
