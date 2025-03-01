package pl.dawidfendler.domain.use_case.currencies_use_case

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.repository.CurrenciesRepository
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

class GetCurrenciesByCodeUseCase @Inject constructor(
    private val currenciesRepository: CurrenciesRepository,
    private val dispatchers: DispatcherProvider
) {

    operator fun invoke(currencyCode: String) = flow {
        try {
            val exchangeRateTable =
                currenciesRepository.getCurrenciesByCode(currencyCode = currencyCode)
            emit(DomainResult.Success(exchangeRateTable))
        } catch (e: Exception) {
            emit(DomainResult.Error(e))
        }
    }.flowOn(dispatchers.io)
}
