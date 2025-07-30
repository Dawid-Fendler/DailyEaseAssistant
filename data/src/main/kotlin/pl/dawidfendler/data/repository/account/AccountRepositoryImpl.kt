package pl.dawidfendler.data.repository.account

import pl.dawidfendler.data.database.account.AccountDao
import pl.dawidfendler.data.mapper.toDomain
import pl.dawidfendler.data.mapper.toEntity
import pl.dawidfendler.domain.model.account.Account
import pl.dawidfendler.domain.repository.account.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dao: AccountDao
): AccountRepository {

    override suspend fun insertOrUpdateAccount(account: Account) {
        dao.insertOrUpdateAccount(account.toEntity())
    }

    override suspend fun insertOrUpdateAccounts(accounts: List<Account>) {
        dao.insertOrUpdateAccounts(accounts.map { it.toEntity() })
    }

    override suspend fun deleteAccount(account: Account) {
        dao.deleteAccount(account.toEntity())
    }

    override suspend fun getAccountsForUser(userId: Long): List<Account> {
        return dao.getAccountsForUser(userId).map { it.toDomain() }
    }

    override suspend fun getAccountByCurrency(userId: Long, currencyCode: String): Account? {
        return dao.getAccountByCurrency(userId, currencyCode)?.toDomain()
    }

    override suspend fun deleteAllAccounts() {
        dao.deleteAllAccounts()
    }
}