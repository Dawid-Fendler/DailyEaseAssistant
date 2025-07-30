package pl.dawidfendler.data.database.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import pl.dawidfendler.data.model.user.UserEntity
import pl.dawidfendler.data.model.user.UserWithAccounts

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUser(userId: Long = 1L): UserEntity?

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserWithAccounts(userId: Long = 1L): UserWithAccounts?

    @Query("DELETE FROM users WHERE userId = :userId")
    suspend fun deleteUser(userId: Long = 1L)
}
