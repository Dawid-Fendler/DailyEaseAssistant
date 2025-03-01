package pl.dawidfendler.finance_manager

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.dawidfendler.FinanceManagerState
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.SELECTED_CURRENCY
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.use_case.authentication_use_case.GetSelectedCurrenciesUseCase
import pl.dawidfendler.domain.use_case.currencies_use_case.GetCurrenciesUseCase
import pl.dawidfendler.domain.use_case.user_use_case.GetAccountBalanceUseCase
import pl.dawidfendler.domain.use_case.user_use_case.UpdateAccountBalanceUseCase
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_VALUE
import pl.dawidfendler.finance_manager.FinanceManagerAction.GetSelectedCurrencies
import pl.dawidfendler.finance_manager.FinanceManagerAction.SavedSelectedCurrencies
import pl.dawidfendler.finance_manager.mapper.formatAccountBalance
import pl.dawidfendler.util.exception.MaxAccountBalanceException
import pl.dawidfendler.util.exception.MinAccountBalanceException
import pl.dawidfendler.util.flow.DomainResult
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class FinanceManagerViewModel @Inject constructor(
    getCurrenciesUseCase: GetCurrenciesUseCase,
    private val dataStore: DataStore,
    private val getSelectedCurrenciesUseCase: GetSelectedCurrenciesUseCase,
    private val updateAccountBalanceUseCase: UpdateAccountBalanceUseCase,
    private val getAccountBalanceUseCase: GetAccountBalanceUseCase
) : ViewModel() {

    var state by mutableStateOf(FinanceManagerState())
        private set

    private val _eventChannel = Channel<FinanceManagerEvent>()
    val eventChannel get() = _eventChannel.receiveAsFlow()

    private fun getAccountBalance() {
        getAccountBalanceUseCase.invoke()
            .onEach { result ->
                when (result) {
                    is DomainResult.Success ->
                        state = state.copy(
                            accountBalance = formatAccountBalance(result.data),
                            accountBalanceColor = if (result.data < BigDecimal.ZERO) {
                                Color.Red
                            } else {
                                Color.Black
                            }
                        )

                    is DomainResult.Error -> {
                        // TODO add error handling
                    }
                }
            }.catch {
                Log.d("FinanceManagerViewModel", "getAccountBalance Error: $it")
                // TODO add error handling
            }.launchIn(viewModelScope)
    }

    init {
        getCurrenciesUseCase.invoke().onEach {
            when (it) {
                is DomainResult.Success -> {
                    val newCurrencies = it.data.toMutableList()
                    newCurrencies.add(
                        0,
                        ExchangeRateTable(
                            currencyName = POLISH_ZLOTY,
                            currencyCode = POLISH_ZLOTY_CODE,
                            currencyMidValue = POLISH_ZLOTY_VALUE
                        )
                    )
                    state = state.copy(
                        currencies = newCurrencies
                    )
                }

                is DomainResult.Error -> {
                    // TODO add error handling
                }
            }
        }.catch {
            // TODO add error handling
        }.launchIn(viewModelScope)

        getAccountBalance()
    }

    fun onAction(action: FinanceManagerAction) {
        when (action) {
            is SavedSelectedCurrencies -> savedSelectedCurrencies(action.selectedCurrencies)
            is GetSelectedCurrencies -> getSelectedCurrencies()
            is FinanceManagerAction.AddMoney -> addMoney(action.addMoney)
            is FinanceManagerAction.SpentMoney -> spentMoney(action.spentMoney)
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
                    selectedCurrency = it
                )
            }.launchIn(viewModelScope)
    }

    private fun addMoney(money: String) {
        updateAccountBalanceUseCase.invoke(
            money = money.toBigDecimal(),
            isAddMoney = true
        ).onEach {
            getAccountBalance()
        }.catch { err ->
            if (err is MaxAccountBalanceException) {
                _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog(err.message))
            } else {
                _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog(null))
            }
            Log.d("FinanceManagerViewModel", "addMoney Error: $err")
        }.launchIn(viewModelScope)
    }

    private fun spentMoney(money: String) {
        updateAccountBalanceUseCase.invoke(
            money = money.toBigDecimal(),
            isAddMoney = false
        ).onEach {
            getAccountBalance()
        }.catch { err ->
            if (err is MinAccountBalanceException) {
                _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog(err.message))
            } else {
                _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog(null))
            }
            Log.d("FinanceManagerViewModel", "spentMoney Error: $err")
        }.launchIn(viewModelScope)
    }
}
