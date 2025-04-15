package pl.dawidfendler.domain.use_case.transaction

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.model.transaction.Transaction
import pl.dawidfendler.domain.repository.TransactionRepository
import pl.dawidfendler.util.flow.DomainResult

class CreateTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val dispatcher: DispatcherProvider
) {

    operator fun invoke(transaction: Transaction) = flow {
        try {
            transactionRepository.insert(transaction)
            emit(DomainResult.Success(Unit))
        } catch (e: Exception) {
            emit(DomainResult.Error(e))
        }
    }.flowOn(dispatcher.io)
}
