package pl.dawidfendler.domain.model.currencies

data class ExchangeRateTable(
    val currencyName: String,
    val currencyCode: String,
    val currencyMidValue: Double? = null,
)
