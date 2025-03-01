package pl.dawidfendler.data.mapper

import pl.dawidfendler.data.model.currency.local.currencies.ExchangeRateTableEntity
import pl.dawidfendler.data.model.currency.remote.ExchangeRateResponse
import pl.dawidfendler.date.DateTimeUtils
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import java.time.OffsetDateTime

internal fun ExchangeRateTableEntity.toDomain() = ExchangeRateTable(
    currencyName = name,
    currencyCode = code,
    currencyMidValue = exchangeRate
)

internal fun ExchangeRateTable.toEntity(dateTimeUtils: DateTimeUtils, tableName: String) = ExchangeRateTableEntity(
    code = currencyCode,
    name = currencyName,
    exchangeRate = currencyMidValue ?: 0.0,
    lastUpdate = dateTimeUtils.convertDateToIsoLocalDateFormat(OffsetDateTime.now()),
    tableName = tableName
)

internal fun ExchangeRateResponse.toDomain() = ExchangeRateTable(
    currencyName = currency ?: "",
    currencyCode = code,
    currencyMidValue = mid
)
