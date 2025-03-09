package pl.dawidfendler.data.model.currencies

import pl.dawidfendler.data.model.currency.local.currencies.ExchangeRateTableEntity
import pl.dawidfendler.data.model.currency.remote.ExchangeRateResponse
import pl.dawidfendler.data.model.currency.remote.ExchangeRateTableResponse
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable

val exchangeRateTableEntityTest = ExchangeRateTableEntity(
    code = "USD",
    name = "dolar",
    exchangeRate = 4.13,
    lastUpdate = "12.10.2025",
    tableName = "TableA"
)

val exchangeRateResponseData = ExchangeRateResponse(
    currency = "dolar",
    code = "USD",
    mid = 4.13
)

val exchangeRateTableResponseData = ExchangeRateTableResponse(
    table = "TableA",
    no = "no",
    effectiveDate = "20.12.2025",
    rates = listOf(exchangeRateResponseData)
)

val exchangeRateTableData = ExchangeRateTable(
    currencyName = "dolar",
    currencyCode = "USD",
    currencyMidValue = 4.13
)


