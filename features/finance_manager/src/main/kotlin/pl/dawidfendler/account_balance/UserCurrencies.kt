package pl.dawidfendler.account_balance

data class UserCurrencies(
    val currencyName: String,
    val accountBalance: String,
    val isDebt: Boolean,
    val isMainItem: Boolean
)
