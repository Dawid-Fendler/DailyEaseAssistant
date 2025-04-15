package pl.dawidfendler.data.datasource.local.user

import pl.dawidfendler.data.database.user.UserDao
import pl.dawidfendler.data.model.user.UserEntity

class UserLocalDataSourceImpl(
    private val userDao: UserDao
) : UserLocalDataSource {
    override suspend fun insert(user: UserEntity) {
        userDao.insert(user)
    }

    override suspend fun getUser(): UserEntity? {
        return userDao.getUser()
    }

    override suspend fun getAccountBalance(): Double? {
        return userDao.getAccountBalance()
    }

    override suspend fun getUserCurrencies(): String? {
        return userDao.getUserCurrencies()
    }

    override suspend fun updateAccountBalance(accountBalance: Double) {
        userDao.updateAccountBalance(accountBalance)
    }

    override suspend fun updateCurrencies(userCurrencies: String) {
        userDao.updateCurrencies(userCurrencies)
    }

    override suspend fun deleteUser() {
        userDao.deleteUser()
    }
}
