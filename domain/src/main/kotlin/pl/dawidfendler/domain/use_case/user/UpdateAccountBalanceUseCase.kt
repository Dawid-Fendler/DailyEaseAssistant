package pl.dawidfendler.domain.use_case.user

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.repository.UserRepository
import pl.dawidfendler.domain.util.Constants.MAX_ACCOUNT_BALANCE
import pl.dawidfendler.domain.util.Constants.MIN_ACCOUNT_BALANCE
import pl.dawidfendler.util.exception.MaxAccountBalanceException
import pl.dawidfendler.util.exception.MinAccountBalanceException
import pl.dawidfendler.util.flow.DomainResult
import java.math.BigDecimal
import javax.inject.Inject

class UpdateAccountBalanceUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: DispatcherProvider
) {

    operator fun invoke(money: BigDecimal, isAddMoney: Boolean) = flow {
        try {
            val actualAccountBalance = userRepository.getAccountBalance()
            val newAccountBalance = if (isAddMoney) {
                if ((actualAccountBalance + money).toLong() > MAX_ACCOUNT_BALANCE) {
                    emit(DomainResult.Error(MaxAccountBalanceException()))
                    return@flow
                } else {
                    actualAccountBalance + money
                }
            } else {
                if ((actualAccountBalance - money).toLong() < MIN_ACCOUNT_BALANCE) {
                    emit(DomainResult.Error(MinAccountBalanceException()))
                    return@flow
                } else {
                    actualAccountBalance - money
                }
            }
            userRepository.updateAccountBalance(newAccountBalance)
            emit(DomainResult.Success(Unit))
        } catch (e: Exception) {
            emit(DomainResult.Error(e))
        }
    }.flowOn(dispatcher.io)
}
