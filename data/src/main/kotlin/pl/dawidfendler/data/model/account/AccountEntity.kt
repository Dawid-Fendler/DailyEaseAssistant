package pl.dawidfendler.data.model.account

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import pl.dawidfendler.data.model.user.UserEntity
import pl.dawidfendler.data.util.Constants.ACCOUNTS_TABLE_NAME

@Entity(
    tableName = ACCOUNTS_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["ownerUserId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["ownerUserId", "currencyCode"],
    indices = [
        Index(value = ["ownerUserId"]),
        Index(value = ["currencyCode"])
    ]
)
data class AccountEntity(
    @JvmField
    val ownerUserId: Long,
    @JvmField
    val currencyCode: String,
    val balance: Double,
    val isMainAccount: Boolean = false,
    val accountName: String
)
