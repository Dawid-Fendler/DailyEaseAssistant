package pl.dawidfendler.data.datasource.local.account

import pl.dawidfendler.data.database.account.AccountDao
import pl.dawidfendler.data.model.account.AccountEntity
import javax.inject.Inject

class AccountLocalDataSourceImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountLocalDataSource {

    override suspend fun insertOrUpdateAccount(account: AccountEntity) {
        accountDao.insertOrUpdateAccount(account)
    }

    override suspend fun insertOrUpdateAccounts(accounts: List<AccountEntity>) {
        accountDao.insertOrUpdateAccounts(accounts)
    }

    override suspend fun deleteAccount(account: AccountEntity) {
        accountDao.deleteAccount(account)
    }

    override suspend fun getAccountsForUser(userId: Long): List<AccountEntity> {
        return accountDao.getAccountsForUser(userId)
    }

    override suspend fun getAccountByCurrency(userId: Long, currencyCode: String): AccountEntity? {
        return accountDao.getAccountByCurrency(userId, currencyCode)
    }

    override suspend fun deleteAllAccounts() {
        accountDao.deleteAllAccounts()
    }
}
