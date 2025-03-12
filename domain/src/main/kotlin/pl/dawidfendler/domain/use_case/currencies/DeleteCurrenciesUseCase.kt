package pl.dawidfendler.domain.use_case.currencies

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.repository.CurrenciesRepository
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

class DeleteCurrenciesUseCase @Inject constructor(
    private val currenciesRepository: CurrenciesRepository,
    private val dispatchers: DispatcherProvider
) {

    operator fun invoke() = flow {
        try {
            currenciesRepository.deleteCurrencies()
            emit(DomainResult.Success(Unit))
        } catch (e: Exception) {
            emit(DomainResult.Error(e))
        }
    }.flowOn(dispatchers.io)
}
