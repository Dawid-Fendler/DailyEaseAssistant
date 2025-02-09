package pl.dawidfendler.data.model.currency.remote

data class ExchangeRateTableResponse(
    val table: String,
    val no: String,
    val effectiveDate: String,
    val rates: List<ExchangeRateResponse>
)

data class ExchangeRateResponse(
    val currency: String?,
    val code: String,
    val mid: Double
)
