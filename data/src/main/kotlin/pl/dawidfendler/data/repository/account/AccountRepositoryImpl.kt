package pl.dawidfendler.data.repository.account

import pl.dawidfendler.data.datasource.local.account.AccountLocalDataSource
import pl.dawidfendler.data.mapper.toDomain
import pl.dawidfendler.data.mapper.toEntity
import pl.dawidfendler.domain.model.account.Account
import pl.dawidfendler.domain.repository.account.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountLocalDataSource: AccountLocalDataSource
) : AccountRepository {

    override suspend fun insertOrUpdateAccount(account: Account) {
        accountLocalDataSource.insertOrUpdateAccount(account.toEntity())
    }

    override suspend fun insertOrUpdateAccounts(accounts: List<Account>) {
        accountLocalDataSource.insertOrUpdateAccounts(accounts.map { it.toEntity() })
    }

    override suspend fun deleteAccount(account: Account) {
        accountLocalDataSource.deleteAccount(account.toEntity())
    }

    override suspend fun getAccountsForUser(userId: Long): List<Account> {
        return accountLocalDataSource.getAccountsForUser(userId).map { it.toDomain() }
    }

    override suspend fun getAccountByCurrency(userId: Long, currencyCode: String): Account? {
        return accountLocalDataSource.getAccountByCurrency(userId, currencyCode)?.toDomain()
    }

    override suspend fun deleteAllAccounts() {
        accountLocalDataSource.deleteAllAccounts()
    }
}