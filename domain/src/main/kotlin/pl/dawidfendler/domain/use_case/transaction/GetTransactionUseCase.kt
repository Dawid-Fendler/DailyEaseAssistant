package pl.dawidfendler.domain.use_case.transaction

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.repository.TransactionRepository
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val dispatcher: DispatcherProvider
) {

    operator fun invoke() = flow {
        try {
            emit(DomainResult.Success(transactionRepository.getTransaction()))
        } catch (e: Exception) {
            emit(DomainResult.Error(e))
        }
    }.flowOn(dispatcher.io)
}
