package pl.dawidfendler.data.datasource.local.user

import pl.dawidfendler.data.model.user.UserEntity
import pl.dawidfendler.data.model.user.UserWithAccounts

interface UserLocalDataSource {

    suspend fun insertOrUpdateUser(user: UserEntity)
    suspend fun getUser(): UserEntity?
    suspend fun getUserWithAccounts(): UserWithAccounts?
    suspend fun updateUser(user: UserEntity)
    suspend fun deleteUser(userId: Long = 1L)
}
