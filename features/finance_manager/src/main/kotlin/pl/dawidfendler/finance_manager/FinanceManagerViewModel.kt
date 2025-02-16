package pl.dawidfendler.finance_manager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.dawidfendler.FinanceManagerState
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.SELECTED_CURRENCY
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.use_case.authentication_use_case.GetSelectedCurrenciesUseCase
import pl.dawidfendler.domain.use_case.currencies_use_case.GetCurrenciesUseCase
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_VALUE
import pl.dawidfendler.util.flow.DomainResult
import pl.dawidfendler.finance_manager.FinanceManagerAction.SavedSelectedCurrencies
import pl.dawidfendler.finance_manager.FinanceManagerAction.GetSelectedCurrencies
import javax.inject.Inject

@HiltViewModel
class FinanceManagerViewModel @Inject constructor(
    getCurrenciesUseCase: GetCurrenciesUseCase,
    private val dataStore: DataStore,
    private val getSelectedCurrenciesUseCase: GetSelectedCurrenciesUseCase
) : ViewModel() {

    var state by mutableStateOf(FinanceManagerState())
        private set

    private val _eventChannel = Channel<FinanceManagerEvent>()
    val eventChannel get() = _eventChannel.receiveAsFlow()

    init {
        getCurrenciesUseCase.invoke().onEach {
            when (it) {
                is DomainResult.Success -> {
                    val newCurrencies = it.data.toMutableList()
                    newCurrencies.add(
                        0, ExchangeRateTable(
                            currencyName = POLISH_ZLOTY,
                            currencyCode = POLISH_ZLOTY_CODE,
                            currencyMidValue = POLISH_ZLOTY_VALUE
                        )
                    )
                    state = state.copy(
                        currencies = newCurrencies.map { currency -> currency.currencyName }
                    )
                }

                is DomainResult.Error -> {

                }
            }
        }.catch {
        }.launchIn(viewModelScope)
    }

    fun onAction(action: FinanceManagerAction) {
        when (action) {
            is SavedSelectedCurrencies -> savedSelectedCurrencies(action.selectedCurrencies)
            is GetSelectedCurrencies -> getSelectedCurrencies()
            is FinanceManagerAction.AddMoney -> Unit
            is FinanceManagerAction.SpentMoney -> Unit
        }
    }


    private fun savedSelectedCurrencies(selectedCurrencies: String) {
        viewModelScope.launch {
            dataStore.putPreference(SELECTED_CURRENCY, selectedCurrencies)
        }
    }

    private fun getSelectedCurrencies() {
        getSelectedCurrenciesUseCase.invoke()
            .onEach {
                state = state.copy(
                    selectedCurrencies = it
                )
            }.launchIn(viewModelScope)
    }
}