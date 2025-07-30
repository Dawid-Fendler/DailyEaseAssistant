package pl.dawidfendler.domain.repository.account

import pl.dawidfendler.domain.model.account.Account

interface AccountRepository {
    suspend fun insertOrUpdateAccount(account: Account)

    suspend fun insertOrUpdateAccounts(accounts: List<Account>)

    suspend fun deleteAccount(account: Account)

    suspend fun getAccountsForUser(userId: Long = 1L): List<Account>

    suspend fun getAccountByCurrency(userId: Long = 1L, currencyCode: String): Account?

    suspend fun deleteAllAccounts()
}
