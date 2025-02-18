package pl.dawidfendler.data.model.currency.local.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.dawidfendler.data.util.Constants.USER_DATABASE_TABLE_NAME

@Entity(tableName = USER_DATABASE_TABLE_NAME)
data class UserEntity(
    @PrimaryKey val id: Int = 0,
    val accountBalance: Double
)
