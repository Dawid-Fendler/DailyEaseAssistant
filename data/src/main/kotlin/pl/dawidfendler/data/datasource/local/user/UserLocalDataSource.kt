package pl.dawidfendler.data.datasource.local.user

import pl.dawidfendler.data.model.currency.local.user.UserEntity

interface UserLocalDataSource {

    suspend fun insert(user: UserEntity)
    suspend fun getUser(): UserEntity?
    suspend fun getAccountBalance(): Double?
    suspend fun updateAccountBalance(accountBalance: Double)
    suspend fun deleteUser()
}