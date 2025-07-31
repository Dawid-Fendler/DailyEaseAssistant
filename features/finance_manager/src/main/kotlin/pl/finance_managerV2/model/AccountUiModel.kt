package pl.finance_managerV2.model

import androidx.compose.ui.graphics.Color

data class AccountUiModel(
    val name: String,
    val currency: String,
    val balance : String,
    val lastTransactionBalance: String? = null,
    val lastTransactionName: String? = null,
    val isExpense: Boolean = false,
    val isMainAccount: Boolean = false,
    val backgroundColor: Color
)
