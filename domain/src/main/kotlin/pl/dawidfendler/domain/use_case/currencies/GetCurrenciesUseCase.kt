package pl.dawidfendler.domain.use_case.currencies

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.repository.finance_manager.CurrenciesRepository
import pl.dawidfendler.domain.util.getPolishCurrency
import pl.dawidfendler.domain.util.mapToCustomException
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult

class GetCurrenciesUseCase(
    private val currenciesRepository: CurrenciesRepository,
    private val dispatchers: DispatcherProvider
) {

    operator fun invoke() = flow {
        val currencies = mutableListOf<ExchangeRateTable>()
        val getTableA = currenciesRepository.getCurrenciesTableA()
        val getTableB = currenciesRepository.getCurrenciesTableB()
        when (getTableA) {
            is DataResult.Success -> { getTableA.data?.let { currencies.addAll(it) } }
            is DataResult.Error -> {
                emit(DomainResult.Error(getTableA.error.mapToCustomException()))
                return@flow
            }
        }

        when (getTableB) {
            is DataResult.Success -> { getTableB.data?.let { currencies.addAll(it) } }

            is DataResult.Error -> {
                emit(DomainResult.Error(getTableB.error.mapToCustomException()))
                return@flow
            }
        }

        currencies.add(0, getPolishCurrency())
        emit(DomainResult.Success(currencies.toList()))
    }.flowOn(dispatchers.io)
}
