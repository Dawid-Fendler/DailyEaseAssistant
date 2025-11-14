package pl.dawidfendler.data.datasource.local.account

import pl.dawidfendler.data.model.account.AccountEntity

interface AccountLocalDataSource {
    suspend fun insertOrUpdateAccount(account: AccountEntity)

    suspend fun insertOrUpdateAccounts(accounts: List<AccountEntity>)

    suspend fun deleteAccount(account: AccountEntity)

    suspend fun getAccountsForUser(userId: Long = 1L): List<AccountEntity>

    suspend fun getAccountByCurrency(userId: Long = 1L, currencyCode: String): AccountEntity?

    suspend fun deleteAllAccounts()
}
