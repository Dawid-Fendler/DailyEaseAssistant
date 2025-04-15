package pl.dawidfendler.domain.repository

import pl.dawidfendler.domain.model.user.User
import java.math.BigDecimal

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun getUser(): User?
    suspend fun getAccountBalance(): BigDecimal
    suspend fun updateAccountBalance(accountBalance: BigDecimal)
    suspend fun getUserCurrencies(): List<String>
    suspend fun updateUserCurrencies(userCurrencies: List<String>)
    suspend fun deleteUser()
}
