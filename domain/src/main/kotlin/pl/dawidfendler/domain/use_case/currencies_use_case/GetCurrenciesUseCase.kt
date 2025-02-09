package pl.dawidfendler.domain.use_case.currencies_use_case

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.repository.CurrenciesRepository
import pl.dawidfendler.domain.util.mapToCustomException
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val currenciesRepository: CurrenciesRepository,
    private val dispatchers: DispatcherProvider
) {

    operator fun invoke() = flow {
        val currencies = mutableListOf<ExchangeRateTable>()
        val getTableA = currenciesRepository.getCurrenciesTableA()
        val getTableB = currenciesRepository.getCurrenciesTableB()
        when (getTableA) {
            is DataResult.Success -> {
                getTableA.data?.let { currencies.addAll(it) }
            }
            is DataResult.Error -> {
                emit(DomainResult.Error(getTableA.error.mapToCustomException()))
                return@flow
            }
        }

        when (getTableB) {
            is DataResult.Success -> {
                getTableB.data?.let { currencies.addAll(it) }
            }

            is DataResult.Error -> {
                emit(DomainResult.Error(getTableB.error.mapToCustomException()))
                return@flow
            }
        }

        emit(DomainResult.Success(currencies.toList()))
    }.flowOn(dispatchers.io)
}