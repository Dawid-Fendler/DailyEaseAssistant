package pl.dawidfendler.domain.use_case.user

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.repository.UserRepository
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

class GetAccountBalanceUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: DispatcherProvider
) {

    operator fun invoke() = flow {
        try {
            val accountBalance = userRepository.getAccountBalance()
            emit(DomainResult.Success(accountBalance))
        } catch (e: Exception) {
            emit(DomainResult.Error(e))
        }
    }.flowOn(dispatcher.io)
}
