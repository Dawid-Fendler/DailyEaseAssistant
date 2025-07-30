package pl.dawidfendler.data.repository

import pl.dawidfendler.data.datasource.local.user.UserLocalDataSource
import pl.dawidfendler.data.mapper.toDomain
import pl.dawidfendler.data.mapper.toEntity
import pl.dawidfendler.domain.model.account.UserWithAccounts
import pl.dawidfendler.domain.model.user.User
import pl.dawidfendler.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun insertOrUpdateUser(user: User) {
        val oldUser = userLocalDataSource.getUser()
        if (oldUser == null) {
            userLocalDataSource.insertOrUpdateUser(user.toEntity())
        }
    }

    override suspend fun getUser(): User? {
        return userLocalDataSource.getUser()?.toDomain()
    }

    override suspend fun getUserWithAccounts(): UserWithAccounts? {
        return userLocalDataSource.getUserWithAccounts()?.let {
            UserWithAccounts(
                user = it.user.toDomain(),
                accounts = it.accounts.map { account -> account.toDomain() }
            )
        }
    }

    override suspend fun updateUser(user: User) {
        userLocalDataSource.updateUser(user.toEntity())
    }

    override suspend fun deleteUser(userId: Long) {
        userLocalDataSource.deleteUser()
    }
}
