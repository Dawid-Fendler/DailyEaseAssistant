package pl.dawidfendler.data.model.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.dawidfendler.data.util.Constants.TRANSACTION_TABLE_NAME

@Entity(tableName = TRANSACTION_TABLE_NAME)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val content: String
)