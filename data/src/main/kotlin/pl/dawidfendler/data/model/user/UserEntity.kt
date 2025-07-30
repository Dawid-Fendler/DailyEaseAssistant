package pl.dawidfendler.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.dawidfendler.data.util.Constants.USERS_TABLE_NAME

@Entity(tableName = USERS_TABLE_NAME)
data class UserEntity(
    @PrimaryKey val userId: Long = 1L,
    val userName: String? = null
)
