package pl.dawidfendler.data.model.currency.local.currencies

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.dawidfendler.data.util.Constants.CURRENCIES_TABLE_NAME

@Entity(tableName = CURRENCIES_TABLE_NAME)
data class ExchangeRateTableEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val code: String,
    val name: String,
    val exchangeRate: Double,
    val lastUpdate: String,
    val tableName: String
)
