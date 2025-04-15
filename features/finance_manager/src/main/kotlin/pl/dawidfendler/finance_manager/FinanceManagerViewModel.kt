package pl.dawidfendler.finance_manager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.dawidfendler.account_balance.UserCurrencies
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.SELECTED_CURRENCY
import pl.dawidfendler.date.DateTime
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.model.transaction.Transaction
import pl.dawidfendler.domain.use_case.currencies.GetCurrenciesUseCase
import pl.dawidfendler.domain.use_case.currencies.GetSelectedCurrenciesUseCase
import pl.dawidfendler.domain.use_case.transaction.CreateTransactionUseCase
import pl.dawidfendler.domain.use_case.transaction.GetTransactionUseCase
import pl.dawidfendler.domain.use_case.user.GetAccountBalanceUseCase
import pl.dawidfendler.domain.use_case.user.GetUserCurrenciesUseCase
import pl.dawidfendler.domain.use_case.user.UpdateAccountBalanceUseCase
import pl.dawidfendler.domain.use_case.user.UpdateUserCurrenciesUseCase
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_VALUE
import pl.dawidfendler.finance_manager.FinanceManagerAction.AddMoney
import pl.dawidfendler.finance_manager.FinanceManagerAction.GetInitialData
import pl.dawidfendler.finance_manager.FinanceManagerAction.GetSelectedCurrencies
import pl.dawidfendler.finance_manager.FinanceManagerAction.GetTransaction
import pl.dawidfendler.finance_manager.FinanceManagerAction.SavedSelectedCurrencies
import pl.dawidfendler.finance_manager.FinanceManagerAction.SpentMoney
import pl.dawidfendler.finance_manager.FinanceManagerAction.UpdateUserSelectedCurrencies
import pl.dawidfendler.finance_manager.mapper.formatAccountBalance
import pl.dawidfendler.finance_manager.mapper.prepareUserCurrencies
import pl.dawidfendler.finance_manager.util.prepareTransactionContent
import pl.dawidfendler.util.exception.MaxAccountBalanceException
import pl.dawidfendler.util.exception.MinAccountBalanceException
import pl.dawidfendler.util.flow.DomainResult
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class FinanceManagerViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val dataStore: DataStore,
    private val getSelectedCurrenciesUseCase: GetSelectedCurrenciesUseCase,
    private val updateAccountBalanceUseCase: UpdateAccountBalanceUseCase,
    private val getAccountBalanceUseCase: GetAccountBalanceUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val dateTime: DateTime,
    private val getUserCurrenciesUseCase: GetUserCurrenciesUseCase,
    private val updateUserCurrenciesUseCase: UpdateUserCurrenciesUseCase
) : ViewModel() {

    var state by mutableStateOf(FinanceManagerState())
        private set

    private val _eventChannel = Channel<FinanceManagerEvent>()
    val eventChannel get() = _eventChannel.receiveAsFlow()

    private fun getAccountBalance() {
        getAccountBalanceUseCase.invoke()
            .onEach { result ->
                when (result) {
                    is DomainResult.Success -> {
                        getSelectedCurrencies()
                        getUserCurrencies(result.data)
                    }

                    is DomainResult.Error -> {
                        _eventChannel.send(
                            FinanceManagerEvent.ShowErrorBottomDialog("Problem with download account balance")
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getInitialData() {
        viewModelScope.launch {
            getCurrenciesUseCase.invoke().onEach {
                when (it) {
                    is DomainResult.Success -> {
                        val newCurrencies = it.data.toMutableList()
                        newCurrencies.add(
                            0,
                            getPolishCurrency()
                        )
                        setStateForFetchCurrencies(currencies = newCurrencies)
                    }

                    is DomainResult.Error -> {
                        //  TODO ADD OWN LOGGER
                        setStateForFetchCurrencies(
                            currencies = listOf(getPolishCurrency()),
                            isFetchError = true
                        )
                        _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog("Problem with download currencies"))
                    }
                }
            }.collect()
            getAccountBalance()
        }
    }

    private fun getPolishCurrency() = ExchangeRateTable(
        currencyName = POLISH_ZLOTY,
        currencyCode = POLISH_ZLOTY_CODE,
        currencyMidValue = POLISH_ZLOTY_VALUE
    )

    private suspend fun defaultAccountBalanceItem(result: BigDecimal) =
        UserCurrencies(
            currencyName = POLISH_ZLOTY_CODE,
            accountBalance = formatAccountBalance(result),
            isDebt = result < BigDecimal.ZERO,
            isMainItem = dataStore.getPreferences(
                SELECTED_CURRENCY,
                POLISH_ZLOTY_CODE
            ).first() == POLISH_ZLOTY_CODE
        )


    private fun setStateForFetchCurrencies(
        currencies: List<ExchangeRateTable>,
        isFetchError: Boolean = false
    ) {
        state = state.copy(
            currencies = currencies,
            isCurrenciesFetchDataError = isFetchError,
            isLoading = false
        )
    }

    fun onAction(action: FinanceManagerAction) {
        when (action) {
            is GetInitialData -> getInitialData()
            is SavedSelectedCurrencies -> savedSelectedCurrencies(action.selectedCurrencies)
            is GetSelectedCurrencies -> getSelectedCurrencies()
            is AddMoney -> addMoney(action.addMoney)
            is SpentMoney -> spentMoney(action.spentMoney)
            is GetTransaction -> getTransaction()
            is UpdateUserSelectedCurrencies -> updateUserSelectedTransaction(action.userCurrencies)
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
                    selectedCurrency = it ?: POLISH_ZLOTY_CODE
                )
            }.launchIn(viewModelScope)
    }

    private fun addMoney(money: String) {
        updateAccountBalanceUseCase.invoke(
            money = money.toBigDecimal(),
            isAddMoney = true
        ).onEach { result ->
            when (result) {
                is DomainResult.Success -> {
                    getAccountBalance()
                    createTransaction(money = money, isAdd = true)
                }

                is DomainResult.Error -> {
                    if (result.error is MaxAccountBalanceException) {
                        _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog(result.error.message))
                    } else {
                        _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog(null))
                    }
                }
            }
        }.catch {
            _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog(null))
            //  TODO ADD OWN LOGGER
        }.launchIn(viewModelScope)
    }

    private fun createTransaction(money: String, isAdd: Boolean) {
        createTransactionUseCase.invoke(
            transaction = Transaction(
                content = prepareTransactionContent(
                    money = money,
                    isAdd = isAdd,
                    date = dateTime.convertDateToDayMonthYearHourMinuteFormat(dateTime.getCurrentDate())
                )
            )
        ).onEach {
            //  TODO ADD OWN LOGGER
        }.catch { err ->
            //  TODO ADD OWN LOGGER
        }.launchIn(viewModelScope)
    }

    private fun spentMoney(money: String) {
        updateAccountBalanceUseCase.invoke(
            money = money.toBigDecimal(),
            isAddMoney = false
        ).onEach { result ->
            when (result) {
                is DomainResult.Success -> {
                    getAccountBalance()
                    createTransaction(money = money, isAdd = false)
                }

                is DomainResult.Error -> {
                    if (result.error is MinAccountBalanceException) {
                        _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog(result.error.message))
                    } else {
                        _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog(null))
                    }
                }
            }
        }.catch {
            _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog(null))
            //  TODO ADD OWN LOGGER
        }.launchIn(viewModelScope)
    }

    private fun getTransaction() {
        getTransactionUseCase.invoke()
            .onEach { result ->
                when (result) {
                    is DomainResult.Success ->
                        if (result.data.isEmpty()) {
                            setStateForFetchTransaction(
                                transaction = result.data,
                                isFetchError = true
                            )
                        } else {
                            setStateForFetchTransaction(
                                transaction = result.data,
                                isFetchError = false
                            )
                        }

                    is DomainResult.Error -> {
                        //  TODO ADD OWN LOGGER
//                        Log.e("FinanceManagerViewModel", "getTransaction Error: ${result.error}")
                        setStateForFetchTransaction(
                            transaction = emptyList(),
                            isFetchError = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun setStateForFetchTransaction(
        transaction: List<Transaction>,
        isFetchError: Boolean
    ) {
        state = state.copy(
            transaction = transaction.map { it.content },
            isTransactionFetchDataError = isFetchError
        )
    }

    private fun getUserCurrencies(data: BigDecimal) {
        getUserCurrenciesUseCase.invoke()
            .onEach { result ->
                when (result) {
                    is DomainResult.Success -> {
                        state = state.copy(
                            userCurrencies = prepareUserCurrencies(
                                currencies = state.currencies,
                                userCurrencies = result.data,
                                accountBalance = state.userCurrencies.firstOrNull()?.accountBalance?.toBigDecimal()
                                    ?: data,
                                selectedCurrencies = state.selectedCurrency,
                                firstUserCurrencies = defaultAccountBalanceItem(data)
                            ),
                            isLoading = false
                        )
                    }

                    is DomainResult.Error -> {
                        //  TODO ADD OWN LOGGER
                    }
                }

            }.launchIn(viewModelScope)
    }

    private fun updateUserSelectedTransaction(userCurrencies: List<String>) {

        updateUserCurrenciesUseCase.invoke(userCurrencies)
            .onEach { result ->
                when (result) {
                    is DomainResult.Error -> {
                        //  TODO ADD OWN LOGGER
                    }
                    is DomainResult.Success -> {
                        val newCurrencies = prepareUserCurrencies(
                            currencies = state.currencies,
                            userCurrencies = userCurrencies,
                            accountBalance = state.userCurrencies.find { it.currencyName == POLISH_ZLOTY_CODE }?.accountBalance?.toBigDecimal()
                                ?: BigDecimal.ZERO,
                            selectedCurrencies = state.selectedCurrency,
                            firstUserCurrencies = state.userCurrencies.first()
                        )
                        state = state.copy(userCurrencies = newCurrencies)
                    }
                }

            }.launchIn(viewModelScope)
    }
}
