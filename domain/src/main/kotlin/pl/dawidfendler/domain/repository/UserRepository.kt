package pl.dawidfendler.domain.repository

import pl.dawidfendler.domain.model.account.UserWithAccounts
import pl.dawidfendler.domain.model.user.User
import java.math.BigDecimal

interface UserRepository {
    suspend fun insertOrUpdateUser(user: User)
    suspend fun getUser(): User?
    suspend fun getUserWithAccounts(): UserWithAccounts?
    suspend fun updateUser(user: User)
    suspend fun deleteUser(userId: Long = 1L)
}
