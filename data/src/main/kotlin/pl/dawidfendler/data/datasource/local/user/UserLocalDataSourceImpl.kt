package pl.dawidfendler.data.datasource.local.user

import pl.dawidfendler.data.database.user.UserDao
import pl.dawidfendler.data.model.user.UserEntity
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {
    override suspend fun insert(user: UserEntity) {
        userDao.insert(user)
    }

    override suspend fun getUser(): UserEntity {
        return userDao.getUser()
    }

    override suspend fun getAccountBalance(): Double? {
        return userDao.getAccountBalance()
    }

    override suspend fun updateAccountBalance(accountBalance: Double) {
        userDao.updateAccountBalance(accountBalance)
    }

    override suspend fun deleteUser() {
        userDao.deleteUser()
    }
}
