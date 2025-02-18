package pl.dawidfendler.data.datasource.database.user

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.dawidfendler.data.model.currency.local.user.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}