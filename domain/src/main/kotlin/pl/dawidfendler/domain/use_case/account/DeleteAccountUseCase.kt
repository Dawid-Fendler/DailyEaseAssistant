package pl.dawidfendler.domain.use_case.account

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.model.account.Account
import pl.dawidfendler.domain.repository.account.AccountRepository
import pl.dawidfendler.util.flow.DomainResult

class DeleteAccountUseCase(
    private val accountRepository: AccountRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke(currencyCode: String, balance: Double, accountName: String) = flow {
        try {
            accountRepository.deleteAccount(Account(currencyCode, balance, accountName = accountName))
            emit(DomainResult.Success(Unit))
        } catch (e: Exception) {
            emit(DomainResult.Error(e))
        }
    }.flowOn(dispatcherProvider.io)
}