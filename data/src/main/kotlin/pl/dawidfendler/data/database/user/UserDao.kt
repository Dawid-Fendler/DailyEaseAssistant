package pl.dawidfendler.data.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.dawidfendler.data.model.user.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM user ")
    suspend fun getUser(): UserEntity?

    @Query("SELECT accountBalance FROM user ")
    suspend fun getAccountBalance(): Double?

    @Query("UPDATE user SET accountBalance = :accountBalance")
    suspend fun updateAccountBalance(accountBalance: Double)

    @Query("SELECT currencies FROM user")
    suspend fun getUserCurrencies(): String?

    @Query("UPDATE user SET currencies = :currencies")
    suspend fun updateCurrencies(currencies: String)

    @Query("DELETE FROM user")
    suspend fun deleteUser()
}
