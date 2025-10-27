package pl.dawidfendler.domain.model.transaction

data class Transaction(
    val amount: Double,
    val date: Long,
    val description: String?,
    val accountName: String,
    val categoryName: String
)
