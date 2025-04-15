package pl.dawidfendler.data.repository

import pl.dawidfendler.data.datasource.local.user.UserLocalDataSource
import pl.dawidfendler.data.mapper.toDomain
import pl.dawidfendler.data.mapper.toEntity
import pl.dawidfendler.data.mapper.userCurrenciesToDomain
import pl.dawidfendler.data.mapper.userCurrenciesToEntity
import pl.dawidfendler.domain.model.user.User
import pl.dawidfendler.domain.repository.UserRepository
import java.math.BigDecimal
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun insertUser(user: User) {
        val oldUser = userLocalDataSource.getUser()
        if (oldUser == null) {
            userLocalDataSource.insert(user.toEntity())
        }
    }

    override suspend fun getUser(): User? {
        return userLocalDataSource.getUser()?.toDomain()
    }

    override suspend fun getAccountBalance(): BigDecimal {
        return userLocalDataSource.getAccountBalance()?.toBigDecimal() ?: BigDecimal.ZERO
    }

    override suspend fun updateAccountBalance(accountBalance: BigDecimal) {
        userLocalDataSource.updateAccountBalance(accountBalance = accountBalance.toDouble())
    }

    override suspend fun getUserCurrencies(): List<String> {
        return userCurrenciesToDomain(userLocalDataSource.getUserCurrencies() ?: "")
    }

    override suspend fun updateUserCurrencies(userCurrencies: List<String>) {
        userLocalDataSource.updateCurrencies(userCurrenciesToEntity(userCurrencies))
    }

    override suspend fun deleteUser() {
        userLocalDataSource.deleteUser()
    }
}
