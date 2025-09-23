package pl.finance_managerV2.dashboard

import androidx.compose.ui.util.fastFirst
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import pl.dawidfendler.domain.model.account.Account
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.use_case.account.GetAccountsForUserUseCase
import pl.dawidfendler.domain.use_case.account.InsertOrUpdateAccountUseCase
import pl.dawidfendler.domain.use_case.currencies.GetCurrenciesUseCase
import pl.dawidfendler.finance_manager.util.getPolishCurrency
import pl.dawidfendler.util.flow.DomainResult
import pl.dawidfendler.util.logger.Logger
import pl.finance_managerV2.mapper.toUiModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAccountForUserUseCase: GetAccountsForUserUseCase,
    private val logger: Logger,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val insertOrUpdateAccountUseCase: InsertOrUpdateAccountUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<DashboardState> = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> get() = _state.asStateFlow()

    private val _eventChannel = Channel<DashboardEvent>()
    val eventChannel get() = _eventChannel.receiveAsFlow()

    private val currencyRefreshTrigger =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    init {
        observeCurrencyRefreshTrigger()
        currencyRefreshTrigger.tryEmit(Unit)
    }

    private fun observeCurrencyRefreshTrigger() {
        currencyRefreshTrigger
            .flatMapLatest {
                _state.update {
                    it.copy(
                        isLoading = true,
                        isFetchDataError = false
                    )
                }
                combine(
                    getAccountForUserUseCase.invoke(),
                    getCurrenciesUseCase.invoke()
                ) { accountsResult: DomainResult<List<Account>, Exception>, currenciesResult: DomainResult<List<ExchangeRateTable>, Exception> ->
                    Pair(accountsResult, currenciesResult)
                }.map { results ->
                    val accounts = when (results.first) {
                        is DomainResult.Error -> {
                            logger.e(
                                "DashboardViewModel",
                                "Error fetching User accounts: ${(results.first as DomainResult.Error).error}"
                            )
                            _eventChannel.trySend(DashboardEvent.ShowErrorBottomDialog("An unexpected error occurred"))
                            setStateForFetchCurrencies(isFetchError = true)
                            emptyList()
                        }

                        is DomainResult.Success -> {
                            logger.d("DashboardViewModel", "Fetched User accounts successfully")
                            (results.first as DomainResult.Success).data
                        }
                    }

                    val currencies = when (results.second) {
                        is DomainResult.Error -> {
                            logger.e(
                                "DashboardViewModel",
                                "Error fetching Currencies: ${(results.second as DomainResult.Error).error}"
                            )
                            _eventChannel.trySend(DashboardEvent.ShowErrorBottomDialog("An unexpected error occurred"))
                            setStateForFetchCurrencies(isFetchError = true)
                            emptyList()
                        }

                        is DomainResult.Success -> {
                            logger.d("DashboardViewModel", "Fetched Currencies successfully")
                            (results.second as DomainResult.Success).data
                        }
                    }

                    setStateForFetchCurrencies(accounts, currencies)
                }
            }.catch { error ->
                logger.e(
                    "DashboardViewModel",
                    "Error fetching Initial Data: $error"
                )
                _eventChannel.trySend(DashboardEvent.ShowErrorBottomDialog("Initial Data Fetch Error"))
                setStateForFetchCurrencies(isFetchError = true)
            }.launchIn(viewModelScope)
    }

    private fun setStateForFetchCurrencies(
        accounts: List<Account> = emptyList(),
        currencies: List<ExchangeRateTable> = listOf(getPolishCurrency()),
        isFetchError: Boolean = false
    ) {
        _state.update {
            it.copy(
                accounts = if (accounts.isEmpty()) emptyList() else accounts.map { account ->
                    account.toUiModel(
                        currencies
                    )
                },
                isFetchDataError = isFetchError,
                isLoading = false,
                finalTotalBalance = if (accounts.isEmpty() || currencies.isEmpty() || (accounts.first { it.isMainAccount }.balance == 0.0)) {
                    "0,00 zł"
                } else {
                    accounts.sumOf { it.balance }.toString() + " "
                            currencies.fastFirst {
                        it.currencyCode == accounts.first {
                            it.isMainAccount
                        }.currencyCode
                    }.currencyCode
                },
                showAddAccountCard = accounts.size < 5,
                accountsCurrencyCode = if (accounts.isEmpty()) emptyList() else accounts.map { it.currencyCode },
                currencies = currencies
            )
        }
    }

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.DataRefresh -> {
                currencyRefreshTrigger.tryEmit(Unit)
            }

            is DashboardAction.ChangeAddAccountBottomSheetVisibility -> {
                _state.update {
                    it.copy(
                        addAccountBottomSheetVisibility = action.isVisible
                    )
                }
            }

            is DashboardAction.AddNewAccount -> {
                addNewAccount(action.accountName, action.currencyCode)
            }
        }
    }

    private fun addNewAccount(accountName: String, currencyCode: String) {
        _state.update {
            it.copy(
                addAccountBottomSheetVisibility = false
            )
        }
        insertOrUpdateAccountUseCase.invoke(
            currencyCode = currencyCode,
            balance = 0.00,
            accountName = accountName,
            isMainAccount = true
        ).onEach {
            currencyRefreshTrigger.tryEmit(Unit)
        }.catch { error ->
            logger.e(
                "DashboardViewModel",
                "Error add new account: $error"
            )
            _eventChannel.trySend(DashboardEvent.ShowErrorBottomDialog("Add account error"))
        }.launchIn(viewModelScope)
    }
}