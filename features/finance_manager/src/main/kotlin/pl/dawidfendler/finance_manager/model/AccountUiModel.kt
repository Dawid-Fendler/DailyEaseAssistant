package pl.dawidfendler.finance_manager.model

data class AccountUiModel(
    val name: String,
    val currency: String,
    val balance : String,
    val lastTransactionBalance: String? = null,
    val lastTransactionName: String? = null,
    val isExpense: Boolean = false,
    val isMainAccount: Boolean = false,
    val mainName: String
)
