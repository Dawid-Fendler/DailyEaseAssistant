package pl.dawidfendler.finance_manager.dashboard

import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.finance_manager.model.AccountUiModel
import pl.finance_manager.transaction_operation.categories.model.CategoryUiModel

data class DashboardState(
    val isLoading: Boolean = true,
    val isFetchDataError: Boolean = false,
    val accounts: List<AccountUiModel> = emptyList(),
    val showAddAccountCard: Boolean = false,
    val finalTotalBalance: String = "0.0",
    val currencies: List<ExchangeRateTable> = emptyList(),
    val accountsCurrencyCode: List<String> = emptyList(),
    val addAccountBottomSheetVisibility: Boolean = false,
    val expenseCategories: List<CategoryUiModel> = emptyList(),
    val incomeCategories: List<CategoryUiModel> = emptyList(),
    val showTransactionOptionDialog: Boolean = false,
    val isExpenseDialog: Boolean = false
)
