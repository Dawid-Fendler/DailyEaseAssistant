package pl.dawidfendler.data.datasource.local.user

import pl.dawidfendler.data.model.user.UserEntity

interface UserLocalDataSource {

    suspend fun insert(user: UserEntity)
    suspend fun getUser(): UserEntity?
    suspend fun getAccountBalance(): Double?
    suspend fun updateAccountBalance(accountBalance: Double)
    suspend fun deleteUser()
}
