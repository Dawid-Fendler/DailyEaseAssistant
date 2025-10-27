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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.dawidfendler.domain.model.account.Account
import pl.dawidfendler.domain.model.categories.FinanceCategory
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.model.transaction.Transaction
import pl.dawidfendler.domain.use_case.account.GetAccountsForUserUseCase
import pl.dawidfendler.domain.use_case.account.InsertOrUpdateAccountUseCase
import pl.dawidfendler.domain.use_case.categories.GetExpenseCategoriesUseCase
import pl.dawidfendler.domain.use_case.categories.GetIncomeCategoriesUseCase
import pl.dawidfendler.domain.use_case.currencies.GetCurrenciesUseCase
import pl.dawidfendler.domain.use_case.transaction.CreateTransactionUseCase
import pl.dawidfendler.finance_manager.util.getPolishCurrency
import pl.dawidfendler.util.flow.DomainResult
import pl.dawidfendler.util.logger.Logger
import pl.finance_managerV2.mapper.toUiModel
import pl.finance_managerV2.transaction_operation.categories.mapper.toCategoryUiModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAccountForUserUseCase: GetAccountsForUserUseCase,
    private val logger: Logger,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val insertOrUpdateAccountUseCase: InsertOrUpdateAccountUseCase,
    private val getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase,
    private val getIncomeCategoriesUseCase: GetIncomeCategoriesUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase
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
                    getCurrenciesUseCase.invoke(),
                    getExpenseCategoriesUseCase.invoke(),
                    getIncomeCategoriesUseCase.invoke()
                ) { accountsResult: DomainResult<List<Account>, Exception>,
                    currenciesResult: DomainResult<List<ExchangeRateTable>, Exception>,
                    expenseCategories: DomainResult<List<FinanceCategory>, Exception>,
                    incomeCategories: DomainResult<List<FinanceCategory>, Exception> ->
                    DashboardCombinedResult(
                        accountsResult,
                        currenciesResult,
                        expenseCategories,
                        incomeCategories
                    )
                }.map { results ->
                    val accounts = when (results.accountsResult) {
                        is DomainResult.Error -> {
                            logger.e(
                                "DashboardViewModel",
                                "Error fetching User accounts: ${results.accountsResult.error}"
                            )
                            _eventChannel.trySend(DashboardEvent.ShowErrorBottomDialog("An unexpected error occurred"))
                            setStateForFetchCurrencies(isFetchError = true)
                            emptyList()
                        }

                        is DomainResult.Success -> {
                            logger.d("DashboardViewModel", "Fetched User accounts successfully")
                            results.accountsResult.data
                        }
                    }

                    val currencies = when (results.currenciesResult) {
                        is DomainResult.Error -> {
                            logger.e(
                                "DashboardViewModel",
                                "Error fetching Currencies: ${results.currenciesResult.error}"
                            )
                            _eventChannel.trySend(DashboardEvent.ShowErrorBottomDialog("An unexpected error occurred"))
                            setStateForFetchCurrencies(isFetchError = true)
                            emptyList()
                        }

                        is DomainResult.Success -> {
                            logger.d("DashboardViewModel", "Fetched Currencies successfully")
                            results.currenciesResult.data
                        }

                    }
                    val expenseCategories = when (results.expenseCategories) {
                        is DomainResult.Error -> {
                            logger.e(
                                "DashboardViewModel",
                                "Error fetching Expense Categories: ${results.expenseCategories.error}"
                            )
                            _eventChannel.trySend(DashboardEvent.ShowErrorBottomDialog("An unexpected error occurred"))
                            emptyList()
                        }

                        is DomainResult.Success -> {
                            logger.d(
                                "DashboardViewModel",
                                "Fetched Expense Categories successfully"
                            )
                            results.expenseCategories.data
                        }
                    }

                    val incomeCategories = when (results.incomeCategories) {
                        is DomainResult.Error -> {
                            logger.e(
                                "DashboardViewModel",
                                "Error fetching Income Categories: ${results.incomeCategories.error}"
                            )
                            _eventChannel.trySend(DashboardEvent.ShowErrorBottomDialog("An unexpected error occurred"))
                            emptyList()
                        }

                        is DomainResult.Success -> {
                            logger.d("DashboardViewModel", "Fetched Income Categories successfully")
                            results.incomeCategories.data
                        }
                    }
                    setStateForFetchCurrencies(
                        accounts,
                        currencies,
                        expenseCategories = expenseCategories,
                        incomeCategories = incomeCategories
                    )
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
        isFetchError: Boolean = false,
        expenseCategories: List<FinanceCategory> = emptyList(),
        incomeCategories: List<FinanceCategory> = emptyList()
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
                finalTotalBalance = if (accounts.isEmpty() || currencies.isEmpty() || (accounts.first { account -> account.isMainAccount }.balance == 0.0)) {
                    "0,00 zł"
                } else {
                    accounts.sumOf { account -> account.balance }.toString() + " " +
                    currencies.fastFirst { currency ->
                        currency.currencyCode == accounts.first { account ->
                            account.isMainAccount
                        }.currencyCode
                    }.currencyCode
                },
                showAddAccountCard = accounts.size < 5,
                accountsCurrencyCode = if (accounts.isEmpty()) emptyList() else accounts.map { account -> account.currencyCode },
                currencies = currencies,
                expenseCategories = expenseCategories.map { category -> category.toCategoryUiModel() },
                incomeCategories = incomeCategories.map { category -> category.toCategoryUiModel() }
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

            is DashboardAction.ChangeTransactionOptionDialogVisibility -> {
                _state.update {
                    it.copy(
                        showTransactionOptionDialog = action.isVisible,
                        isExpenseDialog = action.isExpense
                    )
                }
            }

            is DashboardAction.OnSaveTransactionClick -> {
                _state.update {
                    it.copy(
                        showTransactionOptionDialog = false,
                        isLoading = true
                    )
                }
                addNewTransaction(
                    date = action.date,
                    description = action.description,
                    amount = action.amount,
                    selectedAccount = action.selectedAccount,
                    categoryName = action.categoryName,
                    isExpense = action.isExpense
                )
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

    private fun addNewTransaction(
        date: Long,
        description: String,
        amount: String,
        selectedAccount: String,
        categoryName: String,
        isExpense: Boolean
    ) {
        val accountUiModel = _state.value.accounts.firstOrNull { it.mainName == selectedAccount }
        if (accountUiModel == null) {
            logger.e(
                "DashboardViewModel",
                "Cannot add transaction, account '$selectedAccount' not found in state."
            )
            _state.update { it.copy(isLoading = false) }
            viewModelScope.launch {
                _eventChannel.send(DashboardEvent.ShowErrorBottomDialog("Account not found"))
            }
            return
        }

        val transaction = Transaction(
            amount = amount.toDouble(),
            date = date,
            description = description,
            accountName = selectedAccount,
            categoryName = categoryName
        )

        createTransactionUseCase(transaction)
            .flatMapLatest { transactionResult ->
                when (transactionResult) {
                    is DomainResult.Error -> {
                        logger.e(
                            "DashboardViewModel",
                            "Failed to create transaction: ${transactionResult.error}"
                        )
                        flowOf(DomainResult.Error(transactionResult.error))
                    }

                    is DomainResult.Success -> {
                        logger.d(
                            "DashboardViewModel",
                            "Transaction created successfully, now updating account balance."
                        )
                        val newBalance = if (isExpense) {
                            accountUiModel.balance.toDouble() - amount.toDouble()
                        } else {
                            accountUiModel.balance.toDouble() + amount.toDouble()
                        }

                        insertOrUpdateAccountUseCase(
                            currencyCode = accountUiModel.currency,
                            balance = newBalance,
                            accountName = accountUiModel.mainName,
                            isMainAccount = accountUiModel.isMainAccount
                        )
                    }
                }
            }
            .onEach { finalResult ->
                _state.update { it.copy(isLoading = false) }

                when (finalResult) {
                    is DomainResult.Success -> {
                        logger.d(
                            "DashboardViewModel",
                            "Account updated successfully after transaction."
                        )
                        currencyRefreshTrigger.tryEmit(Unit)
                    }

                    is DomainResult.Error -> {
                        logger.e(
                            "DashboardViewModel",
                            "Failed to update account after transaction: ${finalResult.error}"
                        )
                        _eventChannel.send(DashboardEvent.ShowErrorBottomDialog("Add Transaction Error"))
                    }
                }
            }
            .catch { err ->
                _state.update { it.copy(isLoading = false) }
                logger.e("DashboardViewModel", "Critical error during addNewTransaction flow: $err")
                _eventChannel.send(DashboardEvent.ShowErrorBottomDialog("Add Transaction Error"))
            }
            .launchIn(viewModelScope)
    }
}

private data class DashboardCombinedResult(
    val accountsResult: DomainResult<List<Account>, Exception>,
    val currenciesResult: DomainResult<List<ExchangeRateTable>, Exception>,
    val expenseCategories: DomainResult<List<FinanceCategory>, Exception>,
    val incomeCategories: DomainResult<List<FinanceCategory>, Exception>
)