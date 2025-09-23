package pl.dawidfendler.domain.model.account

data class Account(
    val currencyCode: String,
    val balance: Double,
    val isMainAccount: Boolean = false,
    val accountName: String
)
