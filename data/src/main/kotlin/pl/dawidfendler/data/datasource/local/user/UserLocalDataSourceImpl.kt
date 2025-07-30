package pl.dawidfendler.data.datasource.local.user

import pl.dawidfendler.data.database.users.UserDao
import pl.dawidfendler.data.model.user.UserEntity
import pl.dawidfendler.data.model.user.UserWithAccounts

class UserLocalDataSourceImpl(
    private val userDao: UserDao
) : UserLocalDataSource {
    override suspend fun insertOrUpdateUser(user: UserEntity) {
        userDao.insertOrUpdateUser(user)
    }

    override suspend fun getUser(): UserEntity? {
        return userDao.getUser()
    }

    override suspend fun getUserWithAccounts(): UserWithAccounts? {
        return userDao.getUserWithAccounts()
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(userId: Long) {
        userDao.deleteUser(userId)
    }
}
