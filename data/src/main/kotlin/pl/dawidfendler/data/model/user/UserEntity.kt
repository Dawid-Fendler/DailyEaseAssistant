package pl.dawidfendler.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.dawidfendler.data.util.Constants.USER_TABLE_NAME

@Entity(tableName = USER_TABLE_NAME)
data class UserEntity(
    @PrimaryKey val id: Int = 0,
    val accountBalance: Double,
    val currencies: String
)
